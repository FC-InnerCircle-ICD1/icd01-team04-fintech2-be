package incerpay.paygate.infrastructure.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StoreServiceCaller {

    private static final int KEY_LENGTH = 9;
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String INVALID_API_KEY_MESSAGE = "Non Api Key Accepted";
    private final StoreServiceApi api;
    public StoreServiceCaller(StoreServiceApi api) {
        this.api = api;
    }


    Logger log = LoggerFactory.getLogger(StoreServiceCaller.class);

    public boolean verifyPublicKey(String apiKey, Long sellerId) {

        verifyKey(apiKey);
        api.getApiKeyInfo(apiKey, sellerId);
        verifyPublicKey(apiKey);

        return true;
    }

    public boolean verifySecretKey(String apiKey, Long sellerId){

        verifyKey(apiKey);
        api.getApiKeyInfo(apiKey, sellerId);
        verifySecretKey(apiKey);

        return true;
    }


    private boolean verifySecretKey(String apiKey) {

        if (apiKey.equals(BEARER_PREFIX + "SecretKey")) {
            log.info("Accepted apiKey: {}", apiKey);
            return true;
        }

        throw new RuntimeException(INVALID_API_KEY_MESSAGE);
    }

    private boolean verifyPublicKey(String apiKey) {

        if (apiKey.equals(BEARER_PREFIX + "PublicKey")) {
            log.info("Accepted apiKey: {}", apiKey);
            return true;
        }
        throw new RuntimeException(INVALID_API_KEY_MESSAGE);
    }

    private boolean verifyKey(String apiKey){

        if (apiKey == null) {
            throw new RuntimeException(INVALID_API_KEY_MESSAGE);
        }

        log.info("AuthorizationPublicKeyVerifier apiKey: {}", apiKey);

        if (!apiKey.startsWith(BEARER_PREFIX)) {
            throw new RuntimeException(INVALID_API_KEY_MESSAGE);
        }

        String keyPart = apiKey.substring(BEARER_PREFIX.length());

        log.info("Parsed key length: {}", keyPart.length());

        if (keyPart.length() != KEY_LENGTH) {
            throw new RuntimeException(INVALID_API_KEY_MESSAGE);
        }

        return true;
    }

}
