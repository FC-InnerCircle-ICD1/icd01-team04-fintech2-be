package incerpay.payment.domain.entity;

import incerpay.payment.domain.vo.PaymentState;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;


public class PaymentTest {

    @Nested
    class OfTest{
        @Test
        public void 기본생성of() {
            // Arrange
            String expectedSellerId = "testSeller";
            Long expectedAmount = 100L;
            Instant expectedCreatedAt = Instant.now();

            // Act - 생성
            Payment payment = Payment.of(expectedSellerId, expectedAmount, expectedCreatedAt);

            // Assert
            assertEquals(expectedSellerId, payment.sellerId());
            assertEquals(expectedAmount, payment.paymentProperty().amount());
        }
    }

    @Nested
    class ApproveTest {
        @Test
        public void approve_성공() {
            // Arrange
            String sellerId = "testSeller";
            Long amount = 100L;
            Instant createdAt = Instant.now();
            // 생성
            Payment payment = Payment.of(sellerId, amount, createdAt);
            Clock fixedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

            // Act - 승인
            payment.approve(fixedClock);

            // Assert
            assertEquals(PaymentState.APPROVED, payment.paymentProperty().state());
        }
    }

    @Nested
    class CancelTest {
        @Test
        public void cancel_FromPending_성공() {
            // Arrange
            String sellerId = "testSeller";
            Long amount = 100L;
            Instant createdAt = Instant.now();
            // 생성
            Payment payment = Payment.of(sellerId, amount, createdAt);
            Clock fixedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

            // Act - 취소
            payment.cancel(fixedClock);

            // Assert
            assertEquals(PaymentState.CANCELED, payment.paymentProperty().state());
            assertEquals(1, payment.paymentLedgers().size());
        }

        @Test
        public void cancel_FromApproved_성공() {
            // Arrange
            String sellerId = "testSeller";
            Long amount = 100L;
            // 생성
            Instant createdAt = Instant.now();
            Payment payment = Payment.of(sellerId, amount, createdAt);
            // 승인
            Clock fixedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
            payment.approve(fixedClock);

            // Act - 취소
            payment.cancel(fixedClock);

            // Assert
            assertEquals(PaymentState.CANCELED, payment.paymentProperty().state());
            assertEquals(2, payment.paymentLedgers().size());
        }
    }

    @Nested
    class RejectTest {
        @Test
        public void reject_FromPending_성공() {
            // Arrange
            String sellerId = "testSeller";
            Long amount = 100L;
            Instant createdAt = Instant.now();
            // 생성
            Payment payment = Payment.of(sellerId, amount, createdAt);
            Clock fixedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

            // Act - 거절
            payment.reject(fixedClock);

            // Assert
            assertEquals(PaymentState.REJECTED, payment.paymentProperty().state());
            assertEquals(1, payment.paymentLedgers().size());
        }

        @Test
        public void reconcile_FromApproved_성공() {
            // Arrange
            String userLogin = "testSeller";
            Long boughtProductPrice = 100L;
            Instant buyingTime = Instant.now();
            // Create payment
            Payment payment = Payment.of(userLogin, boughtProductPrice, buyingTime);
            Clock controlledClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
              // Approve payment
            payment.approve(controlledClock);

            // Act - Reconcile
            payment.settle(controlledClock);

            // Assert
            assertEquals(PaymentState.SETTLED, payment.paymentProperty().state());
            assertEquals(2, payment.paymentLedgers().size());
        }
    }
}
