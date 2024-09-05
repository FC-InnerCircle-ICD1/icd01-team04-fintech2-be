package com.incerpay.incerceller.application.port.out;

import com.incerpay.incerceller.adapter.out.persistence.ApiKeyInfoEntity;

public interface SaveLiveApiKeyPort {
    void save(ApiKeyInfoEntity apiKeyInfo);
}
