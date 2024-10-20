package incerpay.paygate.domain.component;

import incerpay.paygate.presentation.dto.CardPaymentDetails;
import incerpay.paygate.presentation.dto.in.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentCardApiMapper {

    public CardApiCertifyCommand toApiCommand(PaymentRequestCommand paymentRequestCommand) {

        log.info("paymentRequestCommand: " + paymentRequestCommand.toString());

        CardPaymentDetails details = (CardPaymentDetails) paymentRequestCommand.paymentMethodDetails();

        return new CardApiCertifyCommand(
                details.getCardNumber(),
                details.getCvc(),
                details.getExpireDate(),
                details.getCardCompany(),
                paymentRequestCommand.sellerId()
        );
    }

    public CardApiCancelCommand toApiCommand(PaymentCancelCommand paymentCancelCommand) {
        log.info("paymentCancelCommand: " + paymentCancelCommand.toString());
        return new CardApiCancelCommand(
                paymentCancelCommand.sellerId(),
                paymentCancelCommand.paymentId(),
                paymentCancelCommand.transactionId()
        );
    }

    public CardApiCancelCommand toApiCommand(PaymentRejectCommand paymentRejectCommand) {
        log.info("paymentRejectCommand: " + paymentRejectCommand.toString());
        return new CardApiCancelCommand(
                paymentRejectCommand.sellerId(),
                paymentRejectCommand.paymentId(),
                paymentRejectCommand.transactionId()
        );
    }

    public CardApiApproveCommand toApiCommand(PaymentApproveCommand paymentApproveCommand) {
        log.info("paymentApproveCommand: " + paymentApproveCommand.toString());

        CardPaymentDetails details = (CardPaymentDetails) paymentApproveCommand.paymentMethodDetails();
        CardApproveDetails approveDetails = (CardApproveDetails) paymentApproveCommand.paymentApproveDetails();


        return new CardApiApproveCommand(
                paymentApproveCommand.sellerId(),
                paymentApproveCommand.customerName(),
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
