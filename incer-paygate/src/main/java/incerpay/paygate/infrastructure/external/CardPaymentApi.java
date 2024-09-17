package incerpay.paygate.infrastructure.external;

import incerpay.paygate.infrastructure.external.dto.*;
import incerpay.paygate.presentation.dto.in.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;


@Component
@FeignClient(name = "CardPaymentApi", url = "http://localhost:8081")
public interface CardPaymentApi {

    @PostMapping("/card/certify")
    CardApiCertifyView certify(CardApiCertifyCommand command);

    @PostMapping("/card/cancel")
    CardApiCancelView cancel(CardApiCancelCommand command);

    @PostMapping("/card/pay")
    CardApiApproveView pay(CardApiApproveCommand command);

}
