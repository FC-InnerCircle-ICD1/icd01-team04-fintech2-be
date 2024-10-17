package incerpay.paygate.domain.component;

import incerpay.paygate.presentation.dto.in.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class IncerPaymentApiMapper {

    public IncerPaymentApiRequestCommand toApiRequestCommand(PaymentRequestCommand paymentRequestCommand) {

        return new IncerPaymentApiRequestCommand(
                paymentRequestCommand.sellerId(),
                paymentRequestCommand.price().longValue(),
                LocalDateTime.now()
        );
    }

    public IncerPaymentApiCancelCommand toApiCancelCommand(PaymentCancelCommand paymentCancelCommand) {
        return new IncerPaymentApiCancelCommand(
                paymentCancelCommand.transactionId()
        );
    }

    public IncerPaymentApiRejectCommand toApiRejectCommand(PaymentRejectCommand paymentRejectCommand) {
        return new IncerPaymentApiRejectCommand(
                paymentRejectCommand.transactionId()
        );
    }

    public IncerPaymentApiApproveCommand toApiApproveCommand(PaymentApproveCommand paymentApproveCommand) {

        return new IncerPaymentApiApproveCommand(
                paymentApproveCommand.transactionId()
        );
    }
}
