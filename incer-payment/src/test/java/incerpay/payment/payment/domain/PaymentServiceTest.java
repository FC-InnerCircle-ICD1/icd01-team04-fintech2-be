package incerpay.payment.payment.domain;

import incerpay.payment.common.dto.*;
import incerpay.payment.domain.PaymentService;
import incerpay.payment.domain.vo.PaymentState;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PaymentServiceTest {

    @Autowired
    private PaymentService service;

    @Nested
    class RequestTest{
        @Test
        public void request_성공() {
            PaymentQuoteCommand command = new PaymentQuoteCommand(
                    "testRequestSellerId",
                    100L,
                    LocalDateTime.now().plusSeconds(30)
            );

            PaymentView actualView = service.quote(command);

            System.out.println(actualView);
            assertNotNull(actualView);
            assertNotNull(actualView.paymentId());
            assertEquals(command.amount(), actualView.amount());
            assertEquals(PaymentState.PENDING, actualView.state());
        }
    }

    @Nested
    class ApproveTest{
        @Test
        public void approve_성공() {
            // Initiate a payment
            PaymentQuoteCommand command = new PaymentQuoteCommand(
                    "testApproveSellerId",
                    100L,
                    LocalDateTime.now().plusSeconds(30)
            );

            // Quote a payment
            PaymentView createdPayment = service.quote(command);
            assertNotNull(createdPayment);
            assertEquals(PaymentState.PENDING, createdPayment.state());

            // Approve the created payment
            PaymentApproveCommand approveCommand = new PaymentApproveCommand(createdPayment.paymentId());
            PaymentView approvedPayment = service.approve(approveCommand);

            // Validate the updated payment status
            assertNotNull(approvedPayment);
            assertEquals(PaymentState.APPROVED, approvedPayment.state());
       }

    }

    @Nested
    class CancelTest{
        @Test
        public void cancel_성공() {
            // Initiate a payment
            PaymentQuoteCommand command = new PaymentQuoteCommand(
                    "testCancelSellerId",
                    100L,
                    LocalDateTime.now().plusSeconds(30)
            );

            // Quote a payment
            PaymentView createdPayment = service.quote(command);
            assertNotNull(createdPayment);
            assertEquals(PaymentState.PENDING, createdPayment.state());
            PaymentApproveCommand approveCommand = new PaymentApproveCommand(createdPayment.paymentId());
            PaymentView approvedPayment = service.approve(approveCommand);
            assertNotNull(approvedPayment);
            assertEquals(PaymentState.APPROVED, approvedPayment.state());

            // Cancel the created payment
            PaymentCancelCommand cancelCommand = new PaymentCancelCommand(createdPayment.paymentId());
            PaymentView cancelledPayment = service.cancel(cancelCommand);

            // Validate the updated payment status
            assertNotNull(cancelledPayment);
            assertEquals(PaymentState.CANCELED, cancelledPayment.state());
       }
    }

    @Nested
    class RejectTest{
        @Test
        public void reject_성공() {
            // Initiate a payment
            PaymentQuoteCommand command = new PaymentQuoteCommand(
                    "testRejectSellerId",
                    100L,
                    LocalDateTime.now().plusSeconds(30)
            );

            // Request a payment
            PaymentView createdPayment = service.quote(command);
            assertNotNull(createdPayment);
            assertEquals(PaymentState.PENDING, createdPayment.state());

            // Reject the created payment
            PaymentRejectCommand rejectCommand = new PaymentRejectCommand(createdPayment.paymentId());
            PaymentView rejectedPayment = service.reject(rejectCommand);

            // Validate the updated payment status
            assertNotNull(rejectedPayment);
            assertEquals(PaymentState.REJECTED, rejectedPayment.state());
        }
    }


    @Nested
    class ReadBySellerIdTest{
        @Test
        public void readBySellerId_성공() {
            // Initialize a payment
            PaymentQuoteCommand command = new PaymentQuoteCommand(
                    "testSellerId",
                    100L,
                    LocalDateTime.now().plusSeconds(30)
            );

            PaymentView createdPayment = service.quote(command);
            assertNotNull(createdPayment);
            assertEquals(PaymentState.PENDING, createdPayment.state());

            PaymentListView paymentListView = service.readBySellerId("testSellerId");

            assertNotNull(paymentListView);
            assertFalse(paymentListView.payments().isEmpty());
            assertEquals(createdPayment, paymentListView.payments().get(0));
        }

        @Test
        public void readBySellerId_없는_sellerId() {
            PaymentListView paymentListView = service.readBySellerId("nonexistentSellerId");

            assertNotNull(paymentListView);
            assertTrue(paymentListView.payments().isEmpty());
        }
    }
}
