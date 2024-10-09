package incerpay.paygate.infrastructure.internal;

import incerpay.paygate.infrastructure.internal.dto.StoreServiceApiCertifyKeyView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class StoreServiceCaller {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String INVALID_API_KEY_MESSAGE = "Non Api Key Accepted";
    private final StoreServiceApi api;
    public StoreServiceCaller(StoreServiceApi api) {
        this.api = api;
    }
    private static final Logger log = LoggerFactory.getLogger(StoreServiceCaller.class);

    public boolean verifyPublicKey(String apiKey, Long sellerId) {

        ResponseEntity<?> view = api.getApiKeyInfo(apiKey, sellerId);
        verifyApiKey(view);

        if (!apiKey.equals(BEARER_PREFIX + "PublicKey")) {
            log.info("Accepted apiKey: {}", apiKey);
            return false;
        }

        return true;
    }

    public boolean verifySecretKey(String apiKey, Long sellerId){

        ResponseEntity<?> view = api.getApiKeyInfo(apiKey, sellerId);
        verifyApiKey(view);

        if (!apiKey.equals(BEARER_PREFIX + "SecretKey")) {
            log.info("Accepted apiKey: {}", apiKey);
            return false;
        }

        return true;
    }


    private boolean verifyApiKey(ResponseEntity<?> rawResponse) {

        if (rawResponse.getBody() instanceof StoreServiceApiCertifyKeyView view
            && rawResponse.getStatusCode().is2xxSuccessful()) {
                String apiKeyState = view.apiKeyState();
                String apiKey = view.apiKey();
                log.info("API Key State: {}, API Key: {}", apiKeyState, apiKey);
                return true;
        }

        throw new RuntimeException(INVALID_API_KEY_MESSAGE);

    }

}
