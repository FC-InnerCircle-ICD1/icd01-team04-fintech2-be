package incerpay.paygate.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import incerpay.paygate.presentation.dto.CardPaymentDetails;
import incerpay.paygate.presentation.dto.in.*;
import incerpay.paygate.domain.enumeration.PaymentType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentGatewayCardCommandAdapterTest {

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void testCardPaymentDetailsMappingForRequest() throws Exception {

        String jsonContent = readJson("src/test/resources/paygate/CardPaymentRequest.json");

        PaymentRequestCommand command = objectMapper.readValue(jsonContent, PaymentRequestCommand.class);

        assertEquals("1", command.sellerId());
        assertEquals("orderId", command.orderId());
        assertEquals(BigDecimal.valueOf(100000), command.price());
        assertEquals(PaymentType.CARD, command.type());
        assertTrue(command.paymentMethodDetails() instanceof CardPaymentDetails);

        CardPaymentDetails paymentDetails = (CardPaymentDetails) command.paymentMethodDetails();
        assertEquals("1234-1234-1234-1234", paymentDetails.getCardNumber());
        assertEquals("123", paymentDetails.getCvc());
        assertEquals("02/27", paymentDetails.getExpireDate());
        assertEquals("030", paymentDetails.getCardCompany());
    }

    @Test
    public void testCardPaymentDetailsMappingForApprove() throws Exception {

        String jsonContent = readJson("src/test/resources/paygate/CardPaymentApprove.json");

        PaymentApproveCommand command = objectMapper.readValue(jsonContent, PaymentApproveCommand.class);

        PaymentApproveCommand expectedCommand = new PaymentApproveCommand(
                "1",
                "customerName",
                "orderId",
                UUID.fromString("8b1806ab-ad22-4a28-9e7a-3bc6cb805d5a"),
                UUID.fromString("c631268d-c4ea-478c-b4c0-50df9b1d1d0c"),
                PaymentType.CARD,
                new CardApproveDetails(3, 10000L, "cert1234"),
                new CardPaymentDetails("1234-5678-9012-3456", "123", "12/26", "030")
        );


        assertTrue(command.paymentApproveDetails() instanceof CardApproveDetails);
        assertTrue(command.paymentMethodDetails() instanceof CardPaymentDetails);

        CardApproveDetails approveDetails = (CardApproveDetails) command.paymentApproveDetails();
        CardApproveDetails expectedApproveDetails = (CardApproveDetails) expectedCommand.paymentApproveDetails();

        CardPaymentDetails paymentDetails = (CardPaymentDetails) command.paymentMethodDetails();
        CardPaymentDetails expectedPaymentDetails = (CardPaymentDetails) expectedCommand.paymentMethodDetails();

        assertEquals(expectedApproveDetails.getInstallmentPeriod(), approveDetails.getInstallmentPeriod());
        assertEquals(expectedApproveDetails.getPrice(), approveDetails.getPrice());
        assertEquals(expectedApproveDetails.getCardIdentityCertifyNumber(), approveDetails.getCardIdentityCertifyNumber());

        assertEquals(expectedPaymentDetails.getCardNumber(), paymentDetails.getCardNumber());
        assertEquals(expectedPaymentDetails.getCvc(), paymentDetails.getCvc());
        assertEquals(expectedPaymentDetails.getExpireDate(), paymentDetails.getExpireDate());
        assertEquals(expectedPaymentDetails.getCardCompany(), paymentDetails.getCardCompany());

    }

    private String readJson(String path) throws Exception {
        return new String(Files.readAllBytes(Paths.get(path)));
    }
}

