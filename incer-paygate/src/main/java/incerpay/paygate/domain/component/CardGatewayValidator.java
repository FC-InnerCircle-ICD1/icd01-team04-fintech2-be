package incerpay.paygate.domain.component;

import incerpay.paygate.domain.vo.PaymentIdentification;
import incerpay.paygate.domain.vo.SellerIdentification;
import incerpay.paygate.domain.vo.TransactionIdentification;
import incerpay.paygate.presentation.dto.in.PaymentApproveCommand;
import incerpay.paygate.presentation.dto.in.PaymentCancelCommand;
import incerpay.paygate.presentation.dto.in.PaymentRejectCommand;
import incerpay.paygate.presentation.dto.in.PaymentRequestCommand;
import org.springframework.stereotype.Component;


@Component
public class CardGatewayValidator implements PaymentGatewayValidator {

    @Override
    public void validate(PaymentRequestCommand command) {}

    @Override
    public void validate(PaymentApproveCommand command) {}

    @Override
    public void validate(PaymentCancelCommand command) {}

    @Override
    public void validate(PaymentRejectCommand command) {}

    @Override
    public void validate(PaymentIdentification command) {}

    @Override
    public void validate(TransactionIdentification command) {}

    @Override
    public void validate(SellerIdentification command) { }
}