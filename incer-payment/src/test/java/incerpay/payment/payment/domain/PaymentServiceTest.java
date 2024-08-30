package incerpay.payment.payment.domain;

import incerpay.payment.payment.domain.dto.*;
import incerpay.payment.payment.domain.vo.PaymentState;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PaymentServiceTest {

    @Autowired
    private PaymentService service;

    @Nested
    class RequestTest{
        @Test
        public void request_성공() {
            PaymentRequestCommand command = new PaymentRequestCommand(
                    "testRequestSellerId",
                    Money.of(100, Currency.getInstance(Locale.KOREA).getCurrencyCode()),
                    LocalDateTime.now().plusSeconds(30)
            );

            PaymentStateView actualView = service.request(command);

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
            PaymentRequestCommand command = new PaymentRequestCommand(
                    "testApproveSellerId",
                    Money.of(100, Currency.getInstance(Locale.KOREA).getCurrencyCode()),
                    LocalDateTime.now().plusSeconds(30)
            );

            // Quote a payment
            PaymentStateView createdPayment = service.request(command);
            assertNotNull(createdPayment);
            assertEquals(PaymentState.PENDING, createdPayment.state());

            // Approve the created payment
            PaymentApproveCommand approveCommand = new PaymentApproveCommand(createdPayment.paymentId());
            PaymentStateView approvedPayment = service.approve(approveCommand);

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
            PaymentRequestCommand command = new PaymentRequestCommand(
                    "testCancelSellerId",
                    Money.of(100, Currency.getInstance(Locale.KOREA).getCurrencyCode()),
                    LocalDateTime.now().plusSeconds(30)
            );

            // Quote a payment
            PaymentStateView createdPayment = service.request(command);
            assertNotNull(createdPayment);
            assertEquals(PaymentState.PENDING, createdPayment.state());
            PaymentApproveCommand approveCommand = new PaymentApproveCommand(createdPayment.paymentId());
            PaymentStateView approvedPayment = service.approve(approveCommand);
            assertNotNull(approvedPayment);
            assertEquals(PaymentState.APPROVED, approvedPayment.state());

            // Cancel the created payment
            PaymentCancelCommand cancelCommand = new PaymentCancelCommand(createdPayment.paymentId());
            PaymentStateView cancelledPayment = service.cancel(cancelCommand);

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
            PaymentRequestCommand command = new PaymentRequestCommand(
                    "testRejectSellerId",
                    Money.of(100, Currency.getInstance(Locale.KOREA).getCurrencyCode()),
                    LocalDateTime.now().plusSeconds(30)
            );

            // Request a payment
            PaymentStateView createdPayment = service.request(command);
            assertNotNull(createdPayment);
            assertEquals(PaymentState.PENDING, createdPayment.state());

            // Reject the created payment
            PaymentRejectCommand rejectCommand = new PaymentRejectCommand(createdPayment.paymentId());
            PaymentStateView rejectedPayment = service.reject(rejectCommand);

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
            PaymentRequestCommand command = new PaymentRequestCommand(
                    "testSellerId",
                    Money.of(100, Currency.getInstance(Locale.KOREA).getCurrencyCode()),
                    LocalDateTime.now().plusSeconds(30)
            );

            PaymentStateView createdPayment = service.request(command);
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
