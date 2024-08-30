package incerpay.payment.payment.infrastructure;

import incerpay.payment.common.lib.jpa.PersistentNotFoundException;
import incerpay.payment.payment.domain.component.PaymentValidator;
import incerpay.payment.payment.domain.dto.PaymentRequestCommand;
import incerpay.payment.payment.domain.dto.PaymentApproveCommand;
import incerpay.payment.payment.domain.dto.PaymentCancelCommand;
import incerpay.payment.payment.domain.dto.PaymentRejectCommand;
import incerpay.payment.payment.domain.entity.Payment;
import incerpay.payment.payment.domain.exception.PaymentStateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PaymentValidatorImplement implements PaymentValidator {

    private final PaymentJpaRepository jpaRepository;

    @Override
    public void validate(PaymentRequestCommand command) {
    }

    @Override
    public void validate(PaymentApproveCommand command) {
        Payment payment = jpaRepository.findById(command.paymentId())
                .orElseThrow(()-> new PersistentNotFoundException("Payment Not Found"));
        if(!payment.isPending()){
            throw new PaymentStateException("Payment Not Pending");
        }
    }

    @Override
    public void validate(PaymentCancelCommand command) {
        Payment payment = jpaRepository.findById(command.paymentId())
                .orElseThrow(()-> new PersistentNotFoundException("Payment Not Found"));
        if(!payment.isApproved()){
            throw new PaymentStateException("Payment Not Approved");
        }
//        if(payment.isFinished()){
//            throw new PaymentStateException("Payment Already Finished");
//        }
    }

    @Override
    public void validate(PaymentRejectCommand command) {
        Payment payment = jpaRepository.findById(command.paymentId())
                .orElseThrow(()-> new PersistentNotFoundException("Payment Not Found"));
        if(!payment.isPending()){
            throw new PaymentStateException("Payment Not Pending");
        }
    }
}
