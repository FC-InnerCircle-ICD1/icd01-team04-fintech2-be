package incerpay.paygate.common.auth;

import incerpay.paygate.infrastructure.internal.IncerPaymentStoreCaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationPublicKeyVerifier {

    // 로그 삭제 여부 확인 필요
    Logger log = LoggerFactory.getLogger(AuthorizationPublicKeyVerifier.class);

    private static final int KEY_LENGTH = 9;
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String INVALID_API_KEY_MESSAGE = "Non Api Key Accepted";

    private final IncerPaymentStoreCaller incerPaymentStoreCaller;
    public AuthorizationPublicKeyVerifier(IncerPaymentStoreCaller incerPaymentStoreCaller) {
        this.incerPaymentStoreCaller = incerPaymentStoreCaller;
    }

    public boolean verify(String apiKey, Long sellerId) {
        validateKey(apiKey);
        incerPaymentStoreCaller.verifyPublicKey(apiKey, sellerId);
        return true;
    }

    private boolean validateKey(String apiKey){

        if (apiKey == null || !apiKey.startsWith(BEARER_PREFIX)) {
            throw new RuntimeException(INVALID_API_KEY_MESSAGE);
        }

        String keyPart = apiKey.substring(BEARER_PREFIX.length());
        log.info("AuthorizationPublicKeyVerifier apiKey: {}, Parsed key length: {}", apiKey, keyPart);

        if (keyPart.length() != KEY_LENGTH) {
            throw new RuntimeException(INVALID_API_KEY_MESSAGE);
        }

        return true;
    }

}
