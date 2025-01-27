package incerpay.paygate.domain.component;

import incerpay.paygate.domain.status.PaymentRealtimeState;
import incerpay.paygate.domain.status.PaymentRealtimeStateService;
import incerpay.paygate.infrastructure.external.dto.IncerPaymentApiDataView;
import incerpay.paygate.infrastructure.internal.IncerPaymentApi;
import incerpay.paygate.infrastructure.internal.dto.IncerPaymentApiView;
import incerpay.paygate.presentation.dto.in.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

import static incerpay.paygate.domain.enumeration.PaymentState.APPROVED;

@Slf4j
@Component
public class PaymentApprovalProcessor {

    private static final int MAX_RETRIES = 3;
    private static final long BASE_RETRY_DELAY_MS = 1000L;
    private final IncerPaymentApi incerPaymentApi;
    private final PaymentRealtimeStateService paymentService;

    public PaymentApprovalProcessor(
            IncerPaymentApi incerPaymentApi,
            PaymentRealtimeStateService paymentService
    ) {
        this.incerPaymentApi = incerPaymentApi;
        this.paymentService = paymentService;
    }

    public IncerPaymentApiView processApproval(
            PaymentApproveCommand paymentApproveCommand
    ) {

        PaymentRealtimeState state = getPaymentRealTimeState(paymentApproveCommand);
        log.debug("Retrieved realtime state: {}", state);

        try {
            return trySyncPaymentApproval(state);
        } catch (RuntimeException e) {
            log.warn("Sync approval failed, attempting async process", e);
            return processAsyncApproval(paymentApproveCommand, state);
        }
    }

    private IncerPaymentApiView trySyncPaymentApproval(PaymentRealtimeState state) {
        IncerPaymentApiView view = tryPaymentApproval(state, 1);
        if (view == null) {
            throw new RuntimeException("Payment approval failed after retries");
        }
        return view;
    }

    private IncerPaymentApiView tryPaymentApproval(PaymentRealtimeState state, int attempt) {
        try {
            return doPaymentApproval(state);
        } catch (RuntimeException e) {
            log.error("Payment approval failed", e);
            handleRetryFailure(state, attempt, e);
            return null;
        }
    }

    private IncerPaymentApiView doPaymentApproval(PaymentRealtimeState state) {
        IncerPaymentApiView view = incerPaymentApi.approve(state.getPaymentApproveCommand());
        log.info("incerPaymentApi.approve: " + view.toString());
        state.makeSaved();
        return view;
    }

    private IncerPaymentApiView processAsyncApproval(
            PaymentApproveCommand paymentApproveCommand,
            PaymentRealtimeState state) {
        try {
            CompletableFuture<IncerPaymentApiView> approvalFuture = executeAsyncApproval(state);
            return approvalFuture.get();
        } catch (Exception ex) {
            log.error("Async approval process failed", ex);
            IncerPaymentApiView fallbackView = createFallbackView(paymentApproveCommand);
            log.info("Created fallback view for payment: {}", fallbackView);
            return fallbackView;
        }
    }

    private CompletableFuture<IncerPaymentApiView> executeAsyncApproval(PaymentRealtimeState state) {
        return new CompletableFuture<IncerPaymentApiView>().completeAsync(() ->
                performPaymentApprovalWithRetry(state)
        );
    }

    private IncerPaymentApiView performPaymentApprovalWithRetry(PaymentRealtimeState state) {
        log.info("Performing payment approval with retry: " + state.toString());

        int firstFallbackCount = 2;
        AtomicReference<IncerPaymentApiView> result = new AtomicReference<>();

        IntStream.rangeClosed(firstFallbackCount, MAX_RETRIES)
                .filter(attempt -> !state.isSaved())
                .mapToObj(attempt -> tryPaymentApproval(state, attempt))
                .filter(Objects::nonNull)
                .findFirst()
                .ifPresent(result::set);

        log.info("Payment approval result: " + result.get().toString());
        return result.get();
    }

    private IncerPaymentApiView createFallbackView(
            PaymentApproveCommand command
    ) {
        return new IncerPaymentApiView(
                200, "fallback view", new IncerPaymentApiDataView(
                command.paymentId(),
                command.sellerId(),
                APPROVED,
                command.paymentApproveDetails().getPrice())
        );
    }


    private void handleRetryFailure(PaymentRealtimeState state, int attempt, RuntimeException e) {
        state.addRetryCount();
        log.warn("Retry attempt {} for payment {}", attempt, state.getPaymentId());

        try {
            Thread.sleep(BASE_RETRY_DELAY_MS * attempt);
        } catch (InterruptedException interruptEx) {
            log.error("Retry delay interrupted", interruptEx);
        }

        log.error("Payment approval failed", e);
    }




    private PaymentRealtimeState getPaymentRealTimeState(PaymentApproveCommand command) {
        log.info("Getting payment real-time state for command: " + command.toString());
        return paymentService.getPaymentRealTimeState(command);
    }

}
