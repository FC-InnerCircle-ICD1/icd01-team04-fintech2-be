package incerpay.paygate.common.auth;

import incerpay.paygate.infrastructure.internal.StoreServiceCaller;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationPublicKeyVerifier {

    private final StoreServiceCaller storeServiceCaller;
    public AuthorizationPublicKeyVerifier(StoreServiceCaller storeServiceCaller) {
        this.storeServiceCaller = storeServiceCaller;
    }

    public boolean verify(String apiKey) {
        storeServiceCaller.verifyPublicKey(apiKey);
        return true;
    }
}
