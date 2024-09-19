package incerpay.payment.domain;

import incerpay.payment.common.dto.*;
import incerpay.payment.common.lib.clock.ClockManager;
import incerpay.payment.common.lib.jpa.PersistentNotFoundException;
import incerpay.payment.domain.component.PaymentFactory;
import incerpay.payment.domain.component.PaymentRepository;
import incerpay.payment.domain.component.PaymentValidator;
import incerpay.payment.domain.component.PaymentViewer;
import incerpay.payment.domain.entity.Payment;
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
    private final PaymentFactory factory;

    @Transactional
    public PaymentView request(PaymentRequestCommand command) {
        Payment payment = factory.create(command);
        repository.save(payment);
        return viewer.readPayment(payment.id());
    }

    @Transactional
    public PaymentView approve(PaymentApproveCommand command) {
        Payment payment = repository.findById(command.paymentId())
                .orElseThrow(()-> new PersistentNotFoundException("Payment Not Found"));
        validator.validateForApprove(payment);
        payment.approve(clockManager.getClock());
        repository.save(payment);
        return viewer.readPayment(payment.id());
    }

    @Transactional
    public PaymentView cancel(PaymentCancelCommand command) {
        Payment payment = repository.findById(command.paymentId())
                .orElseThrow(()-> new PersistentNotFoundException("Payment Not Found"));
        validator.validateForCancel(payment);
        payment.cancel(clockManager.getClock());
        repository.save(payment);
        return viewer.readPayment(payment.id());
    }

    @Transactional
    public PaymentView reject(PaymentRejectCommand command) {
        Payment payment = repository.findById(command.paymentId())
                .orElseThrow(()-> new PersistentNotFoundException("Payment Not Found"));
        validator.validateForReject(payment);
        payment.reject(clockManager.getClock());
        repository.save(payment);
        return viewer.readPayment(payment.id());
    }

    @Transactional(readOnly = true)
    public PaymentListView readBySellerId(String sellerId) {
        return viewer.readBySellerId(sellerId);
    }

    public PaymentDetailView readDetailBySellerId(String sellerId, String paymentId) {
        return viewer.readDetailBySellerId(sellerId, paymentId);
    }

    @Transactional
    public PaymentView confirm(PaymentConfirmCommand command) {
        Payment payment = repository.findById(command.paymentId())
                .orElseThrow(()-> new PersistentNotFoundException("Payment Not Found"));
        validator.validateForConfirm(payment);
        payment.confirm(clockManager.getClock());
        repository.save(payment);
        return viewer.readPayment(payment.id());
    }
}
