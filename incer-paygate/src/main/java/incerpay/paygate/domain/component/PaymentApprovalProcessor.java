package incerpay.paygate.domain.component;

import incerpay.paygate.domain.status.PaymentRealtimeState;
import incerpay.paygate.domain.status.PaymentRealtimeStateService;
import incerpay.paygate.infrastructure.external.dto.IncerPaymentApiDataView;
import incerpay.paygate.infrastructure.internal.IncerPaymentApi;
import incerpay.paygate.infrastructure.internal.dto.IncerPaymentApiView;
import incerpay.paygate.presentation.dto.in.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

import static incerpay.paygate.domain.enumeration.PaymentState.APPROVED;

@Slf4j
@Component
public class PaymentApprovalProcessor {

    private final IncerPaymentApi incerPaymentApi;
    private final PaymentRealtimeStateService paymentService;
    private final ResilienceWrapper resilienceWrapper;

    public PaymentApprovalProcessor(
            IncerPaymentApi incerPaymentApi,
            PaymentRealtimeStateService paymentService,
            ResilienceWrapper resilienceWrapper
    ) {
        this.incerPaymentApi = incerPaymentApi;
        this.paymentService = paymentService;
        this.resilienceWrapper = resilienceWrapper;
    }

    public IncerPaymentApiView processApproval(
            PaymentApproveCommand paymentApproveCommand
    ) {
        PaymentRealtimeState state = getPaymentRealTimeState(paymentApproveCommand);
        log.debug("Retrieved realtime state: {}", state);

        try {
            return resilienceWrapper.execute(() -> doPaymentApproval(state));
        } catch (RuntimeException e) {
            log.warn("Sync approval failed, attempting async process", e);
            return processAsyncApproval(paymentApproveCommand, state);
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
        return resilienceWrapper.executeAsync(() -> performPaymentApprovalWithRetry(state));
    }

    private IncerPaymentApiView performPaymentApprovalWithRetry(PaymentRealtimeState state) {
        log.info("Performing payment approval with retry: " + state.toString());
        if (!state.isSaved()) {
            return doPaymentApproval(state);
        }
        throw new RuntimeException("Payment already saved");
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

    private PaymentRealtimeState getPaymentRealTimeState(PaymentApproveCommand command) {
        log.info("Getting payment real-time state for command: " + command.toString());
        return paymentService.getPaymentRealTimeState(command);
    }
}