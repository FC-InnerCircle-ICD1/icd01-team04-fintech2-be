package com.incerpay.incerceller.application.port.in;

import com.incerpay.incerceller.domain.ApiKeyInfo;

public interface VerifyApiKeyUseCase {
    boolean vertifyApiKey(Long sellerId, ApiKeyInfo apiKeyInfo);

}
