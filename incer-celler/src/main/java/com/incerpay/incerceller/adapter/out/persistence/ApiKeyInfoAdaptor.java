package com.incerpay.incerceller.adapter.out.persistence;

import com.incerpay.incerceller.adapter.out.persistence.jpa.entity.ApiKeyInfoEntity;
import com.incerpay.incerceller.adapter.out.persistence.jpa.repository.ApiKeyInfoRepository;
import com.incerpay.incerceller.application.port.out.SaveLiveApiKeyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ApiKeyInfoAdaptor implements SaveLiveApiKeyPort {

    private final ApiKeyInfoRepository apiKeyInfoRepository;

    @Override
    public void save(ApiKeyInfoEntity apiKeyInfo) {
        apiKeyInfoRepository.save(apiKeyInfo);
    }

}
