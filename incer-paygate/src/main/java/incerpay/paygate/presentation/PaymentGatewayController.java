package incerpay.paygate.presentation;

import incerpay.paygate.application.service.PaymentGatewayService;
import incerpay.paygate.common.lib.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentGatewayController {

    private final PaymentGatewayService service;

    @PostMapping()
    public Response request() {

        return Response.ok("");
    }

}
