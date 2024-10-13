package incerpay.paygate.presentation.api;

import incerpay.paygate.application.service.PaymentGatewayService;
import incerpay.paygate.common.aspect.AuthorizationPublicKeyHeader;
import incerpay.paygate.common.aspect.AuthorizationSecretKeyHeader;
import incerpay.paygate.common.lib.response.Response;
import incerpay.paygate.presentation.dto.in.*;
import incerpay.paygate.presentation.dto.out.PaymentStateView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentGatewayController {

    private final PaymentGatewayService service;

    @AuthorizationPublicKeyHeader
    @PostMapping("/request")
    public Response request(@RequestBody PaymentRequestCommand command) {
        PaymentStateView view = service.request(command);
        return Response.ok(view);
    }

    @AuthorizationPublicKeyHeader
    @PostMapping("/{paymentId}/confirm")
    public Response confirm(@RequestBody PaymentApproveCommand command) {
        PaymentStateView view = service.confirm(command);
        return Response.ok(view);
    }

    @AuthorizationPublicKeyHeader
    @PutMapping("/{paymentId}/cancel")
    public Response cancel(@RequestBody PaymentCancelCommand command) {
        PaymentStateView view = service.cancel(command);
        return Response.ok(view);
    }

    @AuthorizationPublicKeyHeader
    @PutMapping("/{paymentId}/reject")
    public Response reject(@RequestBody PaymentRejectCommand command) {
        PaymentStateView view = service.reject(command);
        return Response.ok(view);
    }

    @AuthorizationSecretKeyHeader
    @GetMapping("/status/payment/{paymentId}")
    public Response readStatusByPaymentId(@RequestParam String paymentId) {
        PaymentStateView view = service.readStatusByPaymentId(paymentId);
        return Response.ok(view);
    }

    @AuthorizationSecretKeyHeader
    @GetMapping("/status/transaction/{transactionId}")
    public Response readStatusByTransactionId(@RequestParam String transactionId) {
        PaymentStateView view = service.readStatusByTransactionId(transactionId);
        return Response.ok(view);
    }

}
