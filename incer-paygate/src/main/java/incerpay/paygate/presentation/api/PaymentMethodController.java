package incerpay.paygate.presentation.api;

import incerpay.paygate.application.service.PaymentMethodService;
import incerpay.paygate.common.aspect.AuthorizationPublicKeyHeader;
import incerpay.paygate.common.lib.response.Response;
import incerpay.paygate.presentation.dto.out.ReadyView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("")
public class PaymentMethodController {
    
    private final PaymentMethodService service;

    @AuthorizationPublicKeyHeader
    @GetMapping("/ready")
    public Response getPaymentInfo(@RequestParam Long sellerId) {
        ReadyView view = service.getPaymentInfo(sellerId);
        return Response.ok(view);
    }

}