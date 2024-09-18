package incerpay.payment.domain.component;

import incerpay.payment.common.exception.PaymentStateException;
import incerpay.payment.domain.entity.Payment;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentValidatorTest {
    private final PaymentValidator validator = new PaymentValidator();

    @Nested
    class ValidateForChangeStateTest {

        @Test
        void validateForChangeState_만료된_결제() {
            Payment payment = Payment.of("testSeller", 100L,
                    Instant.now().minus(10, ChronoUnit.MINUTES));


            assertThrows(PaymentStateException.class, () -> validator.validateForChangeState(payment)
                    , "만료된 결제입니다.");
        }

        @Test
        void testValidateForChangeState_취소된_결제() {
            Payment payment = Payment.of("testSeller", 100L, Instant.now());
            payment.cancel(Clock.system(ZoneId.of("Asia/Seoul")));

            assertThrows(PaymentStateException.class, () -> validator.validateForChangeState(payment)
                    , "이미 취소된 결제입니다.");
        }

        @Test
        void testValidateForChangeState_거절된_결제() {
            Payment payment = Payment.of("testSeller", 100L, Instant.now());
            payment.reject(Clock.system(ZoneId.of("Asia/Seoul")));

            assertThrows(PaymentStateException.class, () -> validator.validateForChangeState(payment)
                    , "결제사 거절");
        }

        @Test
        void testValidateForChangeState_정산된_결제() {
            Payment payment = Payment.of("testSeller", 100L, Instant.now());
            payment.approve(Clock.system(ZoneId.of("Asia/Seoul")));
            payment.settle(Clock.system(ZoneId.of("Asia/Seoul")));

            assertThrows(PaymentStateException.class, () -> validator.validateForChangeState(payment)
                    , "결제 상태가 변경할 수 없습니다.");
        }
    }

    @Nested
    class ValidateForApproveTest {
        @Test
        void validateForApprove_승인된_결제() {
            Payment payment = Payment.of("testSeller", 100L, Instant.now());
            payment.approve(Clock.system(ZoneId.of("Asia/Seoul")));

            assertThrows(PaymentStateException.class, () -> validator.validateForApprove(payment)
                    , "이미 승인된 결제입니다.");
        }
    }


    @Nested
    class ValidateForCancelTest {
        @Test
        void validateForCancel_취소된_결제() {
            Payment payment = Payment.of("testSeller", 100L, Instant.now());
            payment.cancel(Clock.system(ZoneId.of("Asia/Seoul")));

            assertThrows(PaymentStateException.class, () -> validator.validateForCancel(payment)
                    , "이미 취소된 결제입니다.");
        }
    }

    @Nested
    class ValidateForReconcileTest {
        @Test
        void validateForReconcile_정산된_결제() {
            Payment payment = Payment.of("testSeller", 100L, Instant.now());
            payment.approve(Clock.system(ZoneId.of("Asia/Seoul")));
            payment.settle(Clock.system(ZoneId.of("Asia/Seoul")));

            assertThrows(PaymentStateException.class, () -> validator.validateForSettled(payment)
                    , "이미 정산된 결제입니다.");
        }

        @Test
        void validateForReconcile_승인되지_않은_결제() {
            Payment payment = Payment.of("testSeller", 100L, Instant.now());

            assertThrows(PaymentStateException.class, () -> validator.validateForSettled(payment)
                    , "승인된 결제만 정산할 수 있습니다.");
        }
    }

    @Nested
    class ValidateForRejectTest {
        @Test
        void validateForReject_거절된_결제() {
            Payment payment = Payment.of("testSeller", 100L, Instant.now());
            payment.reject(Clock.system(ZoneId.of("Asia/Seoul")));

            assertThrows(PaymentStateException.class, () -> validator.validateForReject(payment)
                    , "이미 거절된 결제입니다.");
        }
    }
}