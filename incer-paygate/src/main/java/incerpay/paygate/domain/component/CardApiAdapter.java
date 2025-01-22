package incerpay.paygate.domain.component;

import incerpay.paygate.domain.status.PaymentRealtimeState;
import incerpay.paygate.domain.status.PaymentRealtimeStateService;
import incerpay.paygate.infrastructure.external.CardPaymentApi;
import incerpay.paygate.infrastructure.external.dto.*;
import incerpay.paygate.presentation.dto.in.*;
import incerpay.paygate.presentation.dto.out.ApiAdapterView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class CardApiAdapter implements PaymentApiAdapter {

    private final CardPaymentApi api;
    private final PaymentCardApiMapper mapper;
    private final PaymentRealtimeStateService paymentService;

    public CardApiAdapter(CardPaymentApi api,
                          PaymentCardApiMapper mapper,
                          PaymentRealtimeStateService paymentService) {
        this.api = api;
        this.mapper = mapper;
        this.paymentService = paymentService;
    }

    @Override
    public ApiAdapterView request(PaymentRequestCommand paymentRequestCommand) {
        CardApiCertifyCommand command = mapper.toApiCommand(paymentRequestCommand);
        CardApiCertifyView view = api.certify(command);
        log.info("api.certify: " + view);

        return createApiAdapterView(view);
    }

    @Override
    public ApiAdapterView cancel(PaymentCancelCommand paymentCancelCommand) {
        CardApiCancelCommand command = mapper.toApiCommand(paymentCancelCommand);
        CardApiCancelView view = api.cancel(command);
        log.info("api.cancel: " + view);

        return createApiAdapterView(view);
    }

    @Override
    public ApiAdapterView reject(PaymentRejectCommand paymentRejectCommand) {
        CardApiCancelCommand command = mapper.toApiCommand(paymentRejectCommand);
        CardApiCancelView view = api.cancel(command);
        log.info("api.reject: " + view);

        return createApiAdapterView(view);
    }

    @Override
    public ApiAdapterView confirm(PaymentApproveCommand paymentApproveCommand) {
        PaymentRealtimeState state = savePaymentRealTimeState(paymentApproveCommand);

        try {
            CardApiApproveView view = executePayment(paymentApproveCommand);
            state.pay();
            log.info("Payment completed successfully: {}", state);
            paymentService.savePayment(state);
            log.info("Payment state saved successfully: {}", state);
            return createApiAdapterView(view);

        } catch (Exception e) {
            if (!handleRetry(state)) {
                log.error("Retry limit exceeded or payment already completed for Payment ID: {}", state.getPaymentId(), e);
            }
            throw e;
        }
    }

    private CardApiApproveView executePayment(PaymentApproveCommand command) {
        CardApiApproveCommand apiCommand = mapper.toApiCommand(command);
        CardApiApproveView view = api.pay(apiCommand);
        log.info("Payment executed successfully: {}", view);
        return view;
    }

    private boolean handleRetry(PaymentRealtimeState state) {
        try {
            state.addRetryCount();
            paymentService.savePayment(state);
            return true;
        } catch (RuntimeException ex) {
            log.warn("Retry failed for Payment ID: {}", state.getPaymentId(), ex);
            return false;
        }
    }

    private ApiAdapterView createApiAdapterView(CardApiCertifyView view) {
        return new ApiAdapterView(
                UUID.randomUUID(),
                UUID.randomUUID(),
                view.sellerId(),
                view.state(),
                view.price()
        );
    }

    private ApiAdapterView createApiAdapterView(CardApiCancelView view) {
        return new ApiAdapterView(
                view.paymentId(),
                UUID.randomUUID(),
                view.sellerId(),
                view.state(),
                0L
        );
    }

    private ApiAdapterView createApiAdapterView(CardApiApproveView view) {
        return new ApiAdapterView(
                view.paymentId(),
                view.transactionId(),
                view.sellerId(),
                view.state(),
                view.price()
        );
    }

    private PaymentRealtimeState savePaymentRealTimeState(PaymentApproveCommand command) {
        PaymentRealtimeState state = new PaymentRealtimeState(command.paymentId().toString(), command.transactionId().toString());
        paymentService.savePayment(state);
        return state;
    }
}