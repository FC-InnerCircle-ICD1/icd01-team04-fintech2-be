package incerpay.paygate.presentation.api;

import incerpay.paygate.application.service.PaymentGatewayService;
import incerpay.paygate.common.lib.response.Response;
import incerpay.paygate.presentation.dto.in.*;
import incerpay.paygate.presentation.dto.out.PaymentStateView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentGatewayController {

    private final PaymentGatewayService service;

    @PostMapping("/request")
    public Response request(@RequestBody PaymentRequestCommand command) {
        PaymentStateView view = service.request(command);
        return Response.ok(view);
    }

    @PostMapping("/confirm")
    public Response confirm(@RequestHeader("Authorization") String publicKey,
                            @RequestBody PaymentApproveCommand command) {
        PaymentStateView view = service.confirm(command);
        return Response.ok(view);
    }

    @PutMapping("/cancel")
    public Response cancel(@RequestHeader("Authorization") String publicKey,
                           @RequestBody PaymentCancelCommand command) {
        PaymentStateView view = service.cancel(command);
        return Response.ok(view);
    }

    @PutMapping("/reject")
    public Response reject(@RequestHeader("Authorization") String publicKey,
                           @RequestBody PaymentRejectCommand command) {
        PaymentStateView view = service.reject(command);
        return Response.ok(view);
    }

    @GetMapping("/status/payment/{paymentId}")
    public Response readStatusByPaymentId(@RequestHeader("Authorization") String secretKey,
                                          @RequestParam String paymentId) {
        PaymentStateView view = service.readStatusByPaymentId(paymentId, secretKey);
        return Response.ok(view);
    }


    @GetMapping("/status/transaction/{transactionId}")
    public Response readStatusByTransactionId(@RequestHeader("Authorization") String secretKey,
                                              @RequestParam String transactionId) {
        PaymentStateView view = service.readStatusByTransactionId(transactionId, secretKey);
        return Response.ok(view);
    }

}
