package incerpay.paygate.infrastructure.internal;

import incerpay.paygate.infrastructure.internal.dto.IncerPayStoreApiCertifyKeyView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


@Component
@FeignClient(name = "IncerPaymentStoreApi", url = "${api.store.url}")
public interface IncerPaymentStoreApi {

    @GetMapping("/apikey")
    ResponseEntity<IncerPayStoreApiCertifyKeyView> getApiKeyInfo(
            @RequestHeader("authorization") String accessToken,
            @RequestParam("sellerId") Long sellerId
    );

}
