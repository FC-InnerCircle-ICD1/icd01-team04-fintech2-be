package com.incerpay.incerceller.application.port.out;

import com.incerpay.incerceller.domain.ApiKeyInfo;

public interface SaveApiKeyPort {
    void save(ApiKeyInfo apiKeyInfo);
}
