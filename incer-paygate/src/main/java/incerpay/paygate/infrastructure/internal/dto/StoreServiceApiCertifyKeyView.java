package incerpay.paygate.infrastructure.internal.dto;


public record StoreServiceApiCertifyKeyView(
        String apiKey,
        String apiKeyState
) {}