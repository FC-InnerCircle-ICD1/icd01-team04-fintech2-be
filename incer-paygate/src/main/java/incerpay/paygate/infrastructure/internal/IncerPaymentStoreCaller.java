package incerpay.paygate.infrastructure.internal;

import incerpay.paygate.common.exception.InvalidApiKeyException;
import incerpay.paygate.domain.enumeration.ApiKeyState;
import incerpay.paygate.infrastructure.internal.dto.ApiKeyInfo;
import incerpay.paygate.infrastructure.internal.dto.SellerApiView;
import incerpay.paygate.infrastructure.internal.dto.TermsApiView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class IncerPaymentStoreCaller {

    private static final String BEARER_PREFIX = "Bearer ";
    private final IncerPaymentStoreApi api;
    public IncerPaymentStoreCaller(IncerPaymentStoreApi api) {
        this.api = api;
    }
    private static final Logger log = LoggerFactory.getLogger(IncerPaymentStoreCaller.class);

    public boolean verifyPublicKey(Long sellerId, String apiKey, ApiKeyState apiKeyState) {

        apiKey = apiKey.substring(BEARER_PREFIX.length());
        ResponseEntity<?> view = api.getApiKeyInfo(sellerId, apiKey, apiKeyState);
        verifyApiKey(view, new ApiKeyInfo(apiKey, apiKeyState));

        return true;
    }

    public boolean verifySecretKey(Long sellerId, String apiKey, ApiKeyState apiKeyState){

        ResponseEntity<?> view = api.getApiKeyInfo(sellerId, apiKey, apiKeyState);
        verifyApiKey(view, new ApiKeyInfo(apiKey, apiKeyState));

        return true;
    }

    public SellerApiView getSeller(Long sellerId) {

        ResponseEntity<SellerApiView> view = api.getSeller(sellerId);
        isValidResponse(view);

        return view.getBody();
    }


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
