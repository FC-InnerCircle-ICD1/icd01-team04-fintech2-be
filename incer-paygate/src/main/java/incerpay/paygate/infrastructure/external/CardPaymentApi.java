package incerpay.paygate.infrastructure.external;

import incerpay.paygate.presentation.dto.in.*;
import org.springframework.stereotype.Component;


@Component
public class CardPaymentApi {

    public void certify(CardApiCertifyCommand command) {}

    public void cancel(CardApiCancelCommand command){}

    public void pay(CardApiApproveCommand command){}
}
