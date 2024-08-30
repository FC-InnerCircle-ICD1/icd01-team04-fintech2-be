package incerpay.payment.payment.domain;

import incerpay.payment.common.lib.clock.ClockManager;
import incerpay.payment.payment.domain.component.PaymentRepository;
import incerpay.payment.payment.domain.component.PaymentValidator;
import incerpay.payment.payment.domain.component.PaymentViewer;
import incerpay.payment.payment.domain.dto.*;
import incerpay.payment.payment.domain.entity.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PaymentService {
    private final PaymentValidator validator;
    private final PaymentRepository repository;
    private final PaymentViewer viewer;
    private final ClockManager clockManager;

    @Transactional
    public PaymentStateView request(PaymentRequestCommand command) {
        validator.validate(command);
        Payment payment = command.toEntity(clockManager.getClock());
        repository.save(payment);
        return viewer.readPayment(payment.id());
    }

    @Transactional
    public PaymentStateView approve(PaymentApproveCommand command) {
        validator.validate(command);
        Payment payment = repository.findById(command.paymentId()).get();
        payment.approve(clockManager.getClock());
        repository.save(payment);
        return viewer.readPayment(payment.id());
    }

    @Transactional
    public PaymentStateView cancel(PaymentCancelCommand command) {
        validator.validate(command);
        Payment payment = repository.findById(command.paymentId()).get();
        payment.cancel(clockManager.getClock());
        repository.save(payment);
        return viewer.readPayment(payment.id());
    }

    @Transactional
    public PaymentStateView reject(PaymentRejectCommand command) {
        validator.validate(command);
        Payment payment = repository.findById(command.paymentId()).get();
        payment.reject(clockManager.getClock());
        repository.save(payment);
        return viewer.readPayment(payment.id());
    }

    public PaymentListView readBySellerId(String sellerId) {
        return viewer.readBySellerId(sellerId);
    }
}
