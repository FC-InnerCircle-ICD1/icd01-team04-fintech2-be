package incerpay.payment.payment.presentation;

import incerpay.payment.common.lib.response.Response;
import incerpay.payment.payment.domain.PaymentService;
import incerpay.payment.payment.domain.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService service;

    @PostMapping("/request")
    public Response request(PaymentRequestCommand command) {
        PaymentStateView view = service.request(command);
        return Response.ok(view);
    }

    @PostMapping("/approve")
    public Response approve(PaymentApproveCommand command) {
        PaymentStateView view = service.approve(command);
        return Response.ok(view);
    }

    @PostMapping("/cancel")
    public Response cancel(PaymentCancelCommand command) {
        PaymentStateView view = service.cancel(command);
        return Response.ok(view);
    }

    @PostMapping("/reject")
    public Response reject(PaymentRejectCommand command) {
        PaymentStateView view = service.reject(command);
        return Response.ok(view);
    }

    @GetMapping("/seller/{sellerId}")
    public Response readBySellerId(String sellerId) {
        PaymentListView view = service.readBySellerId(sellerId);
        return Response.ok(view);
    }
}
