package incerpay.paygate.infrastructure;

import incerpay.paygate.domain.vo.PaymentIdentification;
import incerpay.paygate.domain.vo.TransactionIdentification;
import incerpay.paygate.presentation.dto.in.*;
import org.springframework.stereotype.Component;


@Component
public class CardPaymentApi {

    public void certify(CardApiCertifyCommand command) {}

    public void cancel(CardApiCancelCommand command){}

    public void pay(CardApiApproveCommand cardApiApproveCommand){}

    public void readStatus(TransactionIdentification command){}

    public void readStatus(PaymentIdentification command){ }

}
