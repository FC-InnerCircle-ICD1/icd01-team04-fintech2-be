package incerpay.paygate.domain.component;

import incerpay.paygate.presentation.dto.in.PaymentApproveCommand;
import incerpay.paygate.presentation.dto.in.PaymentCancelCommand;
import incerpay.paygate.presentation.dto.in.PaymentRejectCommand;
import incerpay.paygate.presentation.dto.in.PaymentRequestCommand;
import incerpay.paygate.presentation.dto.out.PersistenceView;

public interface PaymentPersistenceAdapter {

    PersistenceView request(PaymentRequestCommand paymentRequestCommand);
    PersistenceView cancel(PaymentCancelCommand paymentCancelCommand);
    PersistenceView approve(PaymentApproveCommand paymentApproveCommand);
    PersistenceView reject(PaymentRejectCommand paymentRejectCommand);

}

