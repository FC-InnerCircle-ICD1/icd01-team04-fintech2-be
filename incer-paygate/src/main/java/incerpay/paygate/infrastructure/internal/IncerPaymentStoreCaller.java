package incerpay.paygate.infrastructure.internal;

import incerpay.paygate.common.exception.InvalidApiKeyException;
import incerpay.paygate.domain.enumeration.ApiKeyState;
import incerpay.paygate.infrastructure.internal.dto.ApiKeyInfo;
import incerpay.paygate.infrastructure.internal.dto.SellerApiView;
import incerpay.paygate.infrastructure.internal.dto.SellerCacheView;
import incerpay.paygate.infrastructure.internal.dto.TermsApiView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class IncerPaymentStoreCaller {

    private static final String BEARER_PREFIX = "Bearer ";
    private final IncerPaymentStoreApi api;
    private final RedisTemplate<String, Object> redisTemplate;

    public IncerPaymentStoreCaller(IncerPaymentStoreApi api, RedisTemplate<String, Object> redisTemplate) {
        this.api = api;
        this.redisTemplate = redisTemplate;
    }
    private static final Logger log = LoggerFactory.getLogger(IncerPaymentStoreCaller.class);

    public ResponseEntity<Boolean> findKeyResponse(Long sellerId, String apiKey, ApiKeyState apiKeyState) {

        // Boolean 직렬화 저장 불가에 따른 명시적 저장
        String redisKey = "publicKey::" + sellerId + ":" + apiKey + ":" + apiKeyState;

        if(redisTemplate.opsForValue().get(redisKey) != null) {
            return ResponseEntity.ok(true);
        }

        ResponseEntity<Boolean> response = api.getApiKeyInfo(sellerId, apiKey, apiKeyState);
        redisTemplate.opsForValue().set(redisKey, "true");

        return response;
    }


    public boolean verifyPublicKey(Long sellerId, String apiKey, ApiKeyState apiKeyState) {
        String cleanApiKey = apiKey.substring(BEARER_PREFIX.length());
        ResponseEntity<?> response = findKeyResponse(sellerId, cleanApiKey, apiKeyState);
        return verifyApiKey(response, new ApiKeyInfo(cleanApiKey, apiKeyState));
    }

    // 캐시 제외
    public boolean verifySecretKey(Long sellerId, String apiKey, ApiKeyState apiKeyState) {
        return Boolean.TRUE.equals(api.getApiKeyInfo(sellerId, apiKey, apiKeyState).getBody());
    }

    @Cacheable(value = "seller", cacheManager = "redisCacheManager")
    public SellerCacheView getSeller(Long sellerId) {

        ResponseEntity<SellerApiView> view = api.getSeller(sellerId);
        isValidResponse(view);
        return SellerCacheView.from(Objects.requireNonNull(view.getBody()));
    }

    @Cacheable(value = "terms", cacheManager = "caffeineCacheManager")
    public TermsApiView getTerms() {

        ResponseEntity<TermsApiView> view = api.getPaymentTerms();
        isValidResponse(view);

        return view.getBody();
    }


    private boolean isValidResponse(ResponseEntity<?> rawResponse) {
        return rawResponse.getStatusCode().is2xxSuccessful();
    }

    private boolean verifyApiKey(ResponseEntity<?> rawResponse, ApiKeyInfo apiKeyInfo) {

        if (rawResponse.getBody() instanceof Boolean isValidKey
            && rawResponse.getStatusCode().is2xxSuccessful()
            && isValidKey) {
                log.info("API Key State: {}, API Key: {}", apiKeyInfo.getApiKeyState(), apiKeyInfo.getApiKey());
                return true;
        }

        throw new InvalidApiKeyException();

    }

}
