package incerpay.paygate.presentation.api;

import incerpay.paygate.application.service.PaymentMethodService;
import incerpay.paygate.common.lib.response.Response;
import incerpay.paygate.domain.enumeration.PaymentType;
import incerpay.paygate.presentation.dto.out.ReadyView;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("")
public class PaymentMethodController {
    
    private final PaymentMethodService service;

    @GetMapping("/ready")
    public Response getPaymentInfo(HttpServletRequest request) {
        String sellerKey = request.getHeader("X-Client-Id");
        ReadyView view = service.getPaymentInfo(PaymentType.CARD, sellerKey);
        return Response.ok(view);
    }

}