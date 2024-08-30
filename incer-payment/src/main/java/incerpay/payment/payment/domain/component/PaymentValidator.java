package incerpay.payment.payment.domain.component;

import incerpay.payment.payment.domain.dto.PaymentApproveCommand;
import incerpay.payment.payment.domain.dto.PaymentCancelCommand;
import incerpay.payment.payment.domain.dto.PaymentRequestCommand;
import incerpay.payment.payment.domain.dto.PaymentRejectCommand;

public interface PaymentValidator {
    void validate(PaymentRequestCommand command);
    void validate(PaymentApproveCommand command);
    void validate(PaymentCancelCommand command);
    void validate(PaymentRejectCommand command);
}
