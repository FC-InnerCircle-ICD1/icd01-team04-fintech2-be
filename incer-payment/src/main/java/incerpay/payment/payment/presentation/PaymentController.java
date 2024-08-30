package incerpay.payment.payment.presentation;

import incerpay.payment.common.lib.response.Response;
import incerpay.payment.payment.domain.PaymentService;
import incerpay.payment.payment.domain.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService service;

    @PostMapping("/request")
    public Response request(@RequestBody PaymentRequestCommand command) {
        PaymentStateView view = service.request(command);
        return Response.ok(view);
    }

    @PostMapping("/approve")
    public Response approve(@RequestBody PaymentApproveCommand command) {
        PaymentStateView view = service.approve(command);
        return Response.ok(view);
    }

    @PostMapping("/cancel")
    public Response cancel(@RequestBody PaymentCancelCommand command) {
        PaymentStateView view = service.cancel(command);
        return Response.ok(view);
    }

    @PostMapping("/reject")
    public Response reject(@RequestBody PaymentRejectCommand command) {
        PaymentStateView view = service.reject(command);
        return Response.ok(view);
    }

    @GetMapping("/seller/{sellerId}")
    public Response readBySellerId(@PathVariable String sellerId) {
        PaymentListView view = service.readBySellerId(sellerId);
        return Response.ok(view);
    }
}
