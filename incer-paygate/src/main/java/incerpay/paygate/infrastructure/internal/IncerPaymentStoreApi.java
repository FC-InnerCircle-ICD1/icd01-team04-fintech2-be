package incerpay.paygate.infrastructure.internal;

import incerpay.paygate.domain.enumeration.ApiKeyState;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


@Component
@FeignClient(name = "IncerPaymentStoreApi", url = "${api.seller.url}")
public interface IncerPaymentStoreApi {

    @GetMapping("/apikey")
    ResponseEntity<Boolean> getApiKeyInfo(
            @RequestParam("sellerId") Long sellerId,
            @RequestParam("apiKey") String apiKey,
            @RequestParam("apiKeyState") ApiKeyState apiKeyState
    );

}
