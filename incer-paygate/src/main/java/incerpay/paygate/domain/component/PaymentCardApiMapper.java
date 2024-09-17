package incerpay.paygate.domain.component;

import incerpay.paygate.presentation.dto.CardPaymentDetails;
import incerpay.paygate.presentation.dto.in.*;
import org.springframework.stereotype.Component;

@Component
public class PaymentCardApiMapper {

    public CardApiCertifyCommand toApiCertifyCommand(PaymentRequestCommand paymentRequestCommand) {

        CardPaymentDetails details = (CardPaymentDetails) paymentRequestCommand.paymentMethodDetails();

        return new CardApiCertifyCommand(
                details.getCardNumber(),
                details.getCvc(),
                details.getExpireDate(),
                details.getCardCompany(),
                paymentRequestCommand.customerId()
        );
    }

    public CardApiCancelCommand toApiCancelCommand(PaymentCancelCommand paymentCancelCommand) {
        return new CardApiCancelCommand(
                paymentCancelCommand.customerId(),
                paymentCancelCommand.paymentId(),
                paymentCancelCommand.transactionId(),
                paymentCancelCommand.type().name()
        );
    }

    public CardApiCancelCommand toApiCancelCommand(PaymentRejectCommand paymentRejectCommand) {
        return new CardApiCancelCommand(
                paymentRejectCommand.customerId(),
                paymentRejectCommand.paymentId(),
                paymentRejectCommand.transactionId(),
                paymentRejectCommand.type().name()
        );
    }

    public CardApiApproveCommand toApiApproveCommand(PaymentApproveCommand paymentApproveCommand) {

        CardPaymentDetails details = (CardPaymentDetails) paymentApproveCommand.paymentMethodDetails();
        CardApproveDetails approveDetails = (CardApproveDetails) paymentApproveCommand.paymentApproveDetails();

        return new CardApiApproveCommand(
                paymentApproveCommand.customerId(),
                approveDetails.getInstallmentPeriod(),
                approveDetails.getAmount(),
                details.getCardNumber(),
                details.getCvc(),
                details.getExpireDate(),
                details.getCardCompany()
        );
    }
}
