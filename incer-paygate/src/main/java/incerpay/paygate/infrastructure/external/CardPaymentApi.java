package incerpay.paygate.infrastructure.external;

import incerpay.paygate.infrastructure.external.dto.*;
import incerpay.paygate.presentation.dto.in.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;


@Component
@FeignClient(name = "CardPaymentApi", url = "http://localhost:8081")
public interface CardPaymentApi {

    @PostMapping("api/card/certify")
    CardApiCertifyView certify(CardApiCertifyCommand command);

    @PostMapping("api/card/cancel")
    CardApiCancelView cancel(CardApiCancelCommand command);

    @PostMapping("api/card/pay")
    CardApiApproveView pay(CardApiApproveCommand command);

}
