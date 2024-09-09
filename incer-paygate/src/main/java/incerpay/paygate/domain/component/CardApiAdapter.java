package incerpay.paygate.domain.component;

import incerpay.paygate.domain.vo.PaymentIdentification;
import incerpay.paygate.domain.vo.TransactionIdentification;
import incerpay.paygate.infrastructure.external.CardPaymentApi;
import incerpay.paygate.presentation.dto.in.*;
import incerpay.paygate.presentation.dto.out.ApiAdapterView;
import incerpay.paygate.presentation.dto.out.ApiStatusView;
import org.springframework.stereotype.Component;

@Component
public class CardApiAdapter implements PaymentApiAdapter {

    private final CardPaymentApi cardPaymentApi;
    public CardApiAdapter(CardPaymentApi cardPaymentApi) {
        this.cardPaymentApi = cardPaymentApi;
    }

    @Override
    public ApiAdapterView request(PaymentRequestCommand paymentRequestCommand) {
        CardApiCertifyCommand command = null;
        cardPaymentApi.certify(command);
        return null;
    }

    @Override
    public ApiAdapterView cancel(PaymentCancelCommand paymentCancelCommand) {
        CardApiCancelCommand command = null;
        cardPaymentApi.cancel(command);
        return null;
    }

    @Override
    public ApiAdapterView reject(PaymentRejectCommand paymentRejectCommand) {
        CardApiCancelCommand command = null;
        cardPaymentApi.cancel(command);
        return null;
    }

    @Override
    public ApiAdapterView confirm(PaymentApproveCommand paymentApproveCommand) {
        CardApiApproveCommand command = null;
        cardPaymentApi.pay(command);
        return null;
    }

    @Override
    public ApiStatusView readStatus(TransactionIdentification command) {
        cardPaymentApi.readStatus(command);
        return null;
    }

    @Override
    public ApiStatusView readStatus(PaymentIdentification command) {
        cardPaymentApi.readStatus(command);
        return null;
    }

}
