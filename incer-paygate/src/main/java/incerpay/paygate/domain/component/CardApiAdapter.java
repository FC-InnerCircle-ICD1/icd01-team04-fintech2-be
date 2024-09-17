package incerpay.paygate.domain.component;

import incerpay.paygate.infrastructure.external.CardPaymentApi;
import incerpay.paygate.presentation.dto.in.*;
import incerpay.paygate.presentation.dto.out.ApiAdapterView;
import org.springframework.stereotype.Component;

@Component
public class CardApiAdapter implements PaymentApiAdapter {

    private final CardPaymentApi api;
    private final PaymentCardApiMapper mapper;

    public CardApiAdapter(CardPaymentApi api, PaymentCardApiMapper mapper) {
        this.api = api;
        this.mapper = mapper;
    }

    @Override
    public ApiAdapterView request(PaymentRequestCommand paymentRequestCommand) {
        CardApiCertifyCommand command = mapper.toApiCertifyCommand(paymentRequestCommand);
        api.certify(command);
        return createApiAdapterView();
    }

    @Override
    public ApiAdapterView cancel(PaymentCancelCommand paymentCancelCommand) {
        CardApiCancelCommand command = mapper.toApiCancelCommand(paymentCancelCommand);
        api.cancel(command);
        return createApiAdapterView();
    }

    @Override
    public ApiAdapterView reject(PaymentRejectCommand paymentRejectCommand) {
        CardApiCancelCommand command = mapper.toApiCancelCommand(paymentRejectCommand);
        api.cancel(command);
        return createApiAdapterView();
    }

    @Override
    public ApiAdapterView confirm(PaymentApproveCommand paymentApproveCommand) {
        CardApiApproveCommand command = mapper.toApiApproveCommand(paymentApproveCommand);
        api.pay(command);
        return createApiAdapterView();
    }
    
    private ApiAdapterView createApiAdapterView() {
        return null;
    }
}
