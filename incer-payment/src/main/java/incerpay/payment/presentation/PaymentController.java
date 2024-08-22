package incerpay.payment.presentation;

import incerpay.payment.domain.PaymentService;
import incerpay.payment.domain.dto.PaymentCancelCommand;
import incerpay.payment.domain.dto.PayCommand;
import incerpay.payment.domain.dto.PaymentStatusView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService service;

    @RequestMapping("/pay")
    public PaymentResponse pay(PayCommand command) {
        PaymentStatusView view = service.pay(command);
        return PaymentResponse.ok(view);
    }

    @RequestMapping("/cancel")
    public PaymentResponse cancel(PaymentCancelCommand command) {
        PaymentStatusView view = service.cancel(command);
        return PaymentResponse.ok(view);
    }
}
