package incerpay.paygate.domain.component;

import incerpay.paygate.domain.status.PaymentRealtimeState;
import incerpay.paygate.domain.status.PaymentRealtimeStateService;
import incerpay.paygate.infrastructure.external.dto.IncerPaymentApiDataView;
import incerpay.paygate.infrastructure.internal.IncerPaymentApi;
import incerpay.paygate.infrastructure.internal.dto.IncerPaymentApiView;
import incerpay.paygate.presentation.dto.in.*;
import incerpay.paygate.presentation.dto.out.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

import static incerpay.paygate.domain.enumeration.PaymentState.APPROVED;

@Slf4j
@Component
public class PaymentPersistenceAdapter {

    private static final int MAX_RETRIES = 3;
    private static final long BASE_RETRY_DELAY_MS = 1000L;

    private final IncerPaymentApi incerPaymentApi;
    private final IncerPaymentApiMapper incerPaymentApiMapper;
    private final PaymentRealtimeStateService paymentService;

    public PaymentPersistenceAdapter(IncerPaymentApi incerPaymentApi,
                                     IncerPaymentApiMapper incerPaymentApiMapper,
                                     PaymentRealtimeStateService paymentService) {
        this.incerPaymentApi = incerPaymentApi;
        this.incerPaymentApiMapper = incerPaymentApiMapper;
        this.paymentService = paymentService;
    }


    public PersistenceView request(PaymentRequestCommand paymentRequestCommand) {
        IncerPaymentApiRequestCommand command = incerPaymentApiMapper.toApiCommand(paymentRequestCommand);
        IncerPaymentApiView view = incerPaymentApi.request(command);
        log.info("incerPaymentApi.request: " + view.toString());
        return paymentViewToPersistenceView(view);
    }


    public PersistenceView cancel(PaymentCancelCommand paymentCancelCommand) {
        IncerPaymentApiCancelCommand command = incerPaymentApiMapper.toApiCommand(paymentCancelCommand);
        IncerPaymentApiView view = incerPaymentApi.cancel(command);
        log.info("incerPaymentApi.cancel: " + view.toString());
        return paymentViewToPersistenceView(view);
    }


    public PersistenceView reject(PaymentRejectCommand paymentRejectCommand) {
        IncerPaymentApiRejectCommand command = incerPaymentApiMapper.toApiCommand(paymentRejectCommand);
        IncerPaymentApiView view = incerPaymentApi.reject(command);
        log.info("incerPaymentApi.reject: " + view.toString());
        return paymentViewToPersistenceView(view);
    }

    public PersistenceView approve(PaymentApproveCommand paymentApproveCommand) {
        PaymentRealtimeState state = getPaymentRealTimeState(paymentApproveCommand);
        IncerPaymentApiView tempView = createTemporaryView(paymentApproveCommand);
        CompletableFuture<IncerPaymentApiView> approvalFuture = executeAsyncApproval(state);
        IncerPaymentApiView view = processApprovalResult(tempView, approvalFuture);
        return paymentViewToPersistenceView(view);
    }

    private CompletableFuture<IncerPaymentApiView> executeAsyncApproval(PaymentRealtimeState state) {
        return CompletableFuture.supplyAsync(() ->
                performPaymentApprovalWithRetry(state)
        );
    }

    private IncerPaymentApiView performPaymentApprovalWithRetry(PaymentRealtimeState state) {
        log.info("Performing payment approval with retry: " + state.toString());

        AtomicReference<IncerPaymentApiView> result = new AtomicReference<>();

        IntStream.rangeClosed(1, MAX_RETRIES)
                .filter(attempt -> !state.isSaved())
                .mapToObj(attempt -> tryPaymentApproval(state, attempt))
                .filter(Objects::nonNull)
                .findFirst()
                .ifPresent(result::set);

        log.info("Payment approval result: " + result.get().toString());
        return result.get();
    }

    private IncerPaymentApiView tryPaymentApproval(PaymentRealtimeState state, int attempt) {
        try {
            IncerPaymentApiView view = incerPaymentApi.approve(state.getPaymentApproveCommand());
            log.info("incerPaymentApi.approve: " + view.toString());
            state.save();
            return view;
        } catch (RuntimeException e) {
            log.error("Payment approval failed", e);
            handleRetryFailure(state, attempt, e);
            return null;
        }
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

    private IncerPaymentApiView processApprovalResult(
            IncerPaymentApiView tempView,
            CompletableFuture<IncerPaymentApiView> approvalFuture
    ) {
        log.info("Processing approval result: " + tempView.toString());
        return approvalFuture.getNow(tempView);
    }

    private IncerPaymentApiView createTemporaryView(
            PaymentApproveCommand command
    ) {
        return new IncerPaymentApiView(
                200, "Temporary view", new IncerPaymentApiDataView(
                command.paymentId(),
                command.sellerId(),
                APPROVED,
                command.paymentApproveDetails().getPrice())
        );
    }

    private PersistenceView paymentViewToPersistenceView(IncerPaymentApiView view) {
        log.info("Converting IncerPaymentApiView to PersistenceView: " + view.toString());
        return new PersistenceView(
                view.data().paymentId(),
                UUID.randomUUID(),
                view.data().sellerId(),
                view.data().state(),
                view.data().amount()
        );

    }

    private PaymentRealtimeState getPaymentRealTimeState(PaymentApproveCommand command) {
        log.info("Getting payment real-time state for command: " + command.toString());
        return paymentService.getPaymentRealTimeState(command);
    }

}
