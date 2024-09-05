package incerpay.paygate.domain.component;

import incerpay.paygate.domain.vo.PaymentIdentification;
import incerpay.paygate.domain.vo.TransactionIdentification;
import incerpay.paygate.presentation.dto.in.*;

public interface PaymentGatewayValidator {
    void validate(PaymentRequestCommand command);
    void validate(PaymentApproveCommand command);
    void validate(PaymentCancelCommand command);
    void validate(PaymentRejectCommand command);
    void validate(PaymentIdentification command);
    void validate(TransactionIdentification command);
}
