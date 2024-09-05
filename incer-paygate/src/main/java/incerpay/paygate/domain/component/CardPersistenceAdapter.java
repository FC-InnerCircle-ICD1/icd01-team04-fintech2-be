package incerpay.paygate.domain.component;

import incerpay.paygate.presentation.dto.in.PaymentApproveCommand;
import incerpay.paygate.presentation.dto.in.PaymentCancelCommand;
import incerpay.paygate.presentation.dto.in.PaymentRejectCommand;
import incerpay.paygate.presentation.dto.in.PaymentRequestCommand;
import incerpay.paygate.presentation.dto.out.PersistenceView;
import org.springframework.stereotype.Component;

@Component
public class CardPersistenceAdapter implements PaymentPersistenceAdapter{

    @Override
    public PersistenceView request(PaymentRequestCommand paymentRequestCommand) { return null; }

    @Override
    public PersistenceView cancel(PaymentCancelCommand paymentCancelCommand) { return null; }

    @Override
    public PersistenceView approve(PaymentApproveCommand paymentApproveCommand) { return null; }

    @Override
    public PersistenceView reject(PaymentRejectCommand paymentRejectCommand) { return null; }
}
