package incerpay.payment.domain;

import incerpay.payment.common.lib.ClockManager;
import incerpay.payment.domain.component.PaymentRepository;
import incerpay.payment.domain.component.PaymentValidator;
import incerpay.payment.domain.component.PaymentViewer;
import incerpay.payment.domain.dto.PaymentCancelCommand;
import incerpay.payment.domain.dto.PayCommand;
import incerpay.payment.domain.dto.PaymentStatusView;
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
    public PaymentStatusView pay(PayCommand command) {
        validator.validate(command);
        Payment payment = command.toEntity(clockManager.getClock());
        repository.save(payment);
        return viewer.readPayment(payment);
    }

    @Transactional
    public PaymentStatusView cancel(PaymentCancelCommand command) {
        validator.validate(command);
        Payment payment = repository.findById(command.paymentId()).orElseThrow(()-> new RuntimeException("Payment Entity Not Found"));
        payment.cancel(clockManager.getClock());
        repository.save(payment);
        return viewer.readPayment(payment);
    }
}
