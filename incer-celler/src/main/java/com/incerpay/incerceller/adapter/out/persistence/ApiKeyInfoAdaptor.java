package com.incerpay.incerceller.adapter.out.persistence;

import com.incerpay.incerceller.application.port.out.SaveLiveApiKeyPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApiKeyInfoAdaptor implements SaveLiveApiKeyPort {

    private final ApiKeyInfoRepository apiKeyInfoRepository;
    @Override
    public void save(ApiKeyInfoEntity apiKeyInfo) {
        apiKeyInfoRepository.save(apiKeyInfo);
    }

}
