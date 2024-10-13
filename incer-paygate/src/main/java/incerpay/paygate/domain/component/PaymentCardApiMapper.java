package incerpay.paygate.domain.component;

import incerpay.paygate.presentation.dto.CardPaymentDetails;
import incerpay.paygate.presentation.dto.in.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentCardApiMapper {

    public CardApiCertifyCommand toApiCertifyCommand(PaymentRequestCommand paymentRequestCommand) {

        log.info("paymentRequestCommand: " + paymentRequestCommand.toString());

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
        log.info("paymentCancelCommand: " + paymentCancelCommand.toString());
        return new CardApiCancelCommand(
                paymentCancelCommand.customerId(),
                paymentCancelCommand.paymentId(),
                paymentCancelCommand.transactionId()
        );
    }

    public CardApiCancelCommand toApiCancelCommand(PaymentRejectCommand paymentRejectCommand) {
        log.info("paymentRejectCommand: " + paymentRejectCommand.toString());
        return new CardApiCancelCommand(
                paymentRejectCommand.customerId(),
                paymentRejectCommand.paymentId(),
                paymentRejectCommand.transactionId()
        );
    }

    public CardApiApproveCommand toApiApproveCommand(PaymentApproveCommand paymentApproveCommand) {
        log.info("paymentApproveCommand: " + paymentApproveCommand.toString());

        CardPaymentDetails details = (CardPaymentDetails) paymentApproveCommand.paymentMethodDetails();
        CardApproveDetails approveDetails = (CardApproveDetails) paymentApproveCommand.paymentApproveDetails();



        return new CardApiApproveCommand(
                paymentApproveCommand.customerId(),
                approveDetails.getInstallmentPeriod(),
                approveDetails.getPrice(),
                approveDetails.getCardIdentityCertifyNumber(),
                details.getCardNumber(),
                details.getCvc(),
                details.getExpireDate(),
                details.getCardCompany()
        );
    }
}
