package incerpay.paygate.infrastructure.internal.dto;

import incerpay.paygate.domain.enumeration.ApiKeyState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ApiKeyInfo {
    String apiKey;
    ApiKeyState apiKeyState;

    public ApiKeyInfo(String apiKey, String apiKeyState) {
        this.apiKey = apiKey;
        try {
        this.apiKeyState = ApiKeyState.valueOf(apiKeyState);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid API key state: " + apiKeyState);
        }
    }
}
