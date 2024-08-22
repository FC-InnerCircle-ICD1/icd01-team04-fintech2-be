package incerpay.payment.domain.component;

import incerpay.payment.domain.dto.PaymentCancelCommand;
import incerpay.payment.domain.dto.PayCommand;

public interface PaymentValidator {
    void validate(PayCommand command);
    void validate(PaymentCancelCommand command);
}
