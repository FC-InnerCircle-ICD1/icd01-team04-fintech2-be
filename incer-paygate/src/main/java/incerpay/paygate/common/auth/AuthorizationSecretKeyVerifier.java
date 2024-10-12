package incerpay.paygate.common.auth;
import incerpay.paygate.infrastructure.internal.IncerPaymentStoreCaller;
import incerpay.paygate.infrastructure.internal.dto.ApiKeyInfo;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationSecretKeyVerifier {

    private final IncerPaymentStoreCaller incerPaymentStoreCaller;
    public AuthorizationSecretKeyVerifier(IncerPaymentStoreCaller incerPaymentStoreCaller) {
        this.incerPaymentStoreCaller = incerPaymentStoreCaller;
    }

    public boolean verify(Long sellerId, String apiKey, String apiKeyState) {
        ApiKeyInfo apiKeyInfo = new ApiKeyInfo(apiKey, apiKeyState);
        incerPaymentStoreCaller.verifySecretKey(sellerId, apiKey, apiKeyInfo.getApiKeyState());
        return true;
    }

}
