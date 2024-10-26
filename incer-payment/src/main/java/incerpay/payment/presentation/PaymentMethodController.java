package incerpay.payment.presentation;

import incerpay.payment.common.dto.*;
import incerpay.payment.common.lib.response.Response;
import incerpay.payment.domain.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("")
public class PaymentMethodController {

    private final PaymentService service;

    @GetMapping("/cards")
    public Response quote(@RequestBody PaymentQuoteCommand command) {
        PaymentView view = service.quote(command);
        return Response.ok(view);
    }

}
