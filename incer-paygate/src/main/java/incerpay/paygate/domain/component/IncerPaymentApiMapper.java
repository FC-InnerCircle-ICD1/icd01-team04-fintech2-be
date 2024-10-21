package incerpay.paygate.domain.component;

import incerpay.paygate.presentation.dto.in.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class IncerPaymentApiMapper {

    public IncerPaymentApiRequestCommand toApiCommand(PaymentRequestCommand paymentRequestCommand) {

        return new IncerPaymentApiRequestCommand(
                paymentRequestCommand.sellerId(),
                paymentRequestCommand.price().longValue(),
                LocalDateTime.now()
        );
    }

    public IncerPaymentApiCancelCommand toApiCommand(PaymentCancelCommand paymentCancelCommand) {
        return new IncerPaymentApiCancelCommand(
                paymentCancelCommand.paymentId()
        );
    }

    public IncerPaymentApiRejectCommand toApiCommand(PaymentRejectCommand paymentRejectCommand) {
        return new IncerPaymentApiRejectCommand(
                paymentRejectCommand.paymentId()
        );
    }

    public IncerPaymentApiApproveCommand toApiCommand(PaymentApproveCommand paymentApproveCommand) {

        return new IncerPaymentApiApproveCommand(
                paymentApproveCommand.paymentId()
        );
    }
}
