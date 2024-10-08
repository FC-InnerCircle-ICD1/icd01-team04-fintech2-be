package incerpay.paygate.infrastructure.internal;

import incerpay.paygate.infrastructure.internal.dto.StoreServiceApiCertifyKeyView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


@Component
@FeignClient(name = "StoreServiceApi", url = "http://localhost:8083")
public interface StoreServiceApi {

    @GetMapping("/apikey")
    StoreServiceApiCertifyKeyView getApiKeyInfo(
            @RequestHeader("authorization") String accessToken,
            @RequestParam("sellerId") Long sellerId
    );

}
