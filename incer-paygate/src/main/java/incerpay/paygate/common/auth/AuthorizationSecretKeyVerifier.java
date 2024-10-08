package incerpay.paygate.common.auth;
import incerpay.paygate.infrastructure.internal.StoreServiceCaller;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationSecretKeyVerifier {

    private final StoreServiceCaller storeServiceCaller;
    public AuthorizationSecretKeyVerifier(StoreServiceCaller storeServiceCaller) {
        this.storeServiceCaller = storeServiceCaller;
    }

    public boolean verify(String apiKey, Long sellerId) {
        storeServiceCaller.verifySecretKey(apiKey, sellerId);
        return true;
    }

}
