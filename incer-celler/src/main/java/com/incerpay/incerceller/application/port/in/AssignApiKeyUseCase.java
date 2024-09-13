package com.incerpay.incerceller.application.port.in;

import com.incerpay.incerceller.domain.ApiKeyState;

public interface AssignApiKeyUseCase {
    String assignApiKey(Long sellerId, ApiKeyState apiKeyState);

}
