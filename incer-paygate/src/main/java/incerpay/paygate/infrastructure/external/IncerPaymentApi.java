package incerpay.paygate.infrastructure.external;

import incerpay.paygate.common.config.FeignConfig;
import incerpay.paygate.infrastructure.external.dto.IncerPaymentApiListView;
import incerpay.paygate.infrastructure.external.dto.IncerPaymentApiView;
import incerpay.paygate.presentation.dto.in.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Component
@FeignClient(name = "IncerPaymentApi", url = "http://localhost:8082", configuration = FeignConfig.class)
public interface IncerPaymentApi {

    @PostMapping("/payment/quote")
    IncerPaymentApiView request(@RequestBody IncerPaymentApiRequestCommand command);

    @PostMapping("/payment/approve")
    IncerPaymentApiView approve(@RequestBody IncerPaymentApiApproveCommand command);

    @PostMapping("/payment/cancel")
    IncerPaymentApiView cancel(@RequestBody IncerPaymentApiCancelCommand command);

    @PostMapping("/payment/reject")
    IncerPaymentApiView reject(@RequestBody IncerPaymentApiRejectCommand command);

    @GetMapping("/payment/seller/{sellerId}")
    IncerPaymentApiListView readBySellerId(@PathVariable("sellerId") String sellerId);

}
