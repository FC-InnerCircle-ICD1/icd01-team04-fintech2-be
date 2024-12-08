package incerpay.payment.domain.component;

import incerpay.payment.common.exception.PaymentThrottlingException;
import incerpay.payment.common.exception.PaymentStateException;
import incerpay.payment.common.lib.clock.ClockManager;
import incerpay.payment.domain.entity.Payment;
import incerpay.payment.domain.vo.PaymentState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@RequiredArgsConstructor
@Component
public class PaymentValidator {


    private final ClockManager clockManager;
    private final PaymentRepository repository;

    public void validateForQuote(Payment payment) {

        String sellerId = payment.sellerId();

        Instant threshold = clockManager.getClock().instant()
                .minus(Duration.ofSeconds(3));

        boolean hasRecentRequest = repository.existsBySellerIdAndCreatedAtAfter(
                sellerId, threshold);

        if (hasRecentRequest) {
            throw new PaymentThrottlingException("3초 이내 재시도는 불가능합니다.");
        }
    }

    public void validateForChangeState(Payment payment) {
        if(payment.isExpired()){
            throw new PaymentStateException("만료된 결제입니다.");
        }
        if (payment.isFinished()) {
            switch (payment.paymentProperty().state()) {
                case PaymentState.CANCELED:
                    throw new PaymentStateException("이미 취소된 결제입니다.");
                case PaymentState.REJECTED:
                    throw new PaymentStateException("결제사 거절");
                case PaymentState.SETTLED:
                default:
                    throw new PaymentStateException("결제 상태가 변경할 수 없습니다.");
            }
        }
    }

    public void validateForApprove(Payment payment) {
        if(payment.paymentProperty().state() == PaymentState.APPROVED){
            throw new PaymentStateException("이미 승인된 결제입니다.");
        }
        validateForChangeState(payment);
    }

    public void validateForConfirm(Payment payment) {
        if(payment.paymentProperty().state() == PaymentState.CONFIRMED){
            throw new PaymentStateException("이미 결제 확인된 결제입니다.");
        }
        if(payment.paymentProperty().state() != PaymentState.APPROVED){
            throw new PaymentStateException("승인된 결제만 결제 확인할 수 있습니다.");
        }
        validateForChangeState(payment);
    }

    public void validateForCancel(Payment payment) {
        if(payment.paymentProperty().state() == PaymentState.CANCELED){
            throw new PaymentStateException("이미 취소된 결제입니다.");
        }
        validateForChangeState(payment);
    }

    public void validateForSettled(Payment payment) {
        if(payment.paymentProperty().state() == PaymentState.SETTLED){
            throw new PaymentStateException("이미 정산된 결제입니다.");
        }
        if(payment.paymentProperty().state() != PaymentState.CONFIRMED){
            throw new PaymentStateException("확인된 결제만 정산할 수 있습니다.");
        }
        validateForChangeState(payment);
    }

    public void validateForReject(Payment payment) {
        if(payment.paymentProperty().state() == PaymentState.REJECTED){
            throw new PaymentStateException("이미 거절된 결제입니다.");
        }
        if(payment.paymentProperty().state() == PaymentState.APPROVED){
            throw new PaymentStateException("승인된 결제는 거절할 수 없습니다.");
        }
        validateForChangeState(payment);
    }


}
