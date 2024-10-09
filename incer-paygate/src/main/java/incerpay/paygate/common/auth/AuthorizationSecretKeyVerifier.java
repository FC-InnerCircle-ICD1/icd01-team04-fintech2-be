package incerpay.paygate.common.auth;
import incerpay.paygate.infrastructure.internal.IncerPaymentStoreCaller;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationSecretKeyVerifier {

    private final IncerPaymentStoreCaller incerPaymentStoreCaller;
    public AuthorizationSecretKeyVerifier(IncerPaymentStoreCaller incerPaymentStoreCaller) {
        this.incerPaymentStoreCaller = incerPaymentStoreCaller;
    }

    public boolean verify(String apiKey, Long sellerId) {
        incerPaymentStoreCaller.verifySecretKey(apiKey, sellerId);
        return true;
    }

}
