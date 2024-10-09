package incerpay.paygate.infrastructure.internal.dto;


public record IncerPayStoreApiCertifyKeyView(
        String apiKey,
        String apiKeyState
) {}