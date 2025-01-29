package incerpay.paygate.domain.component;

import incerpay.paygate.domain.log.PaymentApiLogService;
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
    private final PaymentApiLogService paymentApiLogService;

    public CardApiAdapter(CardPaymentApi api,
                          PaymentCardApiMapper mapper,
                          PaymentRealtimeStateService paymentService,
                          PaymentApiLogService paymentLogService) {
        this.api = api;
        this.mapper = mapper;
        this.paymentService = paymentService;
        this.paymentApiLogService = paymentLogService;
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
    public ApiAdapterView confirm(PaymentApproveCommand command) {
        PaymentRealtimeState state = savePaymentRealTimeState(command);
        CardApiApproveView view = executePayment(command);
        state.makePaid(); // isPaid 업데이트
        return createApiAdapterView(view);
    }

    private PaymentRealtimeState savePaymentRealTimeState(PaymentApproveCommand command) {
        PaymentRealtimeState state = new PaymentRealtimeState(command);
        paymentService.savePayment(state);
        return state;
    }

    private CardApiApproveView executePayment(PaymentApproveCommand command) {

        log.info("Initiating payment execution for paymentId: {}", command.paymentId());

        logAndSavePaymentRequest(command); 
        CardApiApproveView view = processExternalPayment(command);
        logAndSavePaymentResponse(command.paymentId(), view);

        log.info("Payment executed successfully: {}", view);

        return view;
    }

    private void logAndSavePaymentRequest(PaymentApproveCommand command) {
        log.info("Saving and logging payment request : {}", command);
        try {
            CardApiApproveCommand apiCommand = mapper.toApiCommand(command);
            paymentApiLogService.saveRequestLog(command.paymentId(), apiCommand);
            log.info("Saved request log for paymentId: {}", command.paymentId());
        } catch (Exception e) {
            log.error("Critical: Failed to save payment request log. PaymentId: {}", command.paymentId(), e);
            throw new RuntimeException("결제 요청 기록 저장 실패. 결제를 진행할 수 없습니다.", e);
        }
    }

    private CardApiApproveView processExternalPayment(PaymentApproveCommand command) {
        log.info("Initiating external payment API call for paymentId: {}", command.paymentId());
        try {
            CardApiApproveCommand apiCommand = mapper.toApiCommand(command);
            CardApiApproveView response = api.pay(apiCommand);
            log.info("Received response from external payment API: {}", response);
            return response;
        } catch (Exception e) {
            log.error("External payment API call failed. PaymentId: {}", command.paymentId(), e);
            throw new RuntimeException("External payment failed", e);
        }
    }

    private void logAndSavePaymentResponse(UUID paymentId, CardApiApproveView response) {
        log.info("Saving and logging payment response: {}", response);
        try {
            paymentApiLogService.saveResponseLog(paymentId, response);
            log.info("Saved response log for paymentId: {}", paymentId);
        } catch (Exception e) {
            log.error("Critical: Failed to save payment response log. PaymentId: {}", paymentId, e);
            // TODO 관리자 알림 발송
            paymentApiLogService.saveErrorLog(paymentId, e);
            throw new RuntimeException("결제 응답 기록 저장 실패. 결제 취소가 필요할 수 있습니다.", e);
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
}