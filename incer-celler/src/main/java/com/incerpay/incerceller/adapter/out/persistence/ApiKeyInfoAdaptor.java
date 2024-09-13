package com.incerpay.incerceller.adapter.out.persistence;

import com.incerpay.incerceller.adapter.out.persistence.jpa.entity.ApiKeyInfoEntity;
import com.incerpay.incerceller.adapter.out.persistence.jpa.repository.ApiKeyInfoRepository;
import com.incerpay.incerceller.application.port.out.SaveLiveApiKeyPort;
import com.incerpay.incerceller.domain.ApiKeyInfo;
import com.incerpay.incerceller.mapper.ApikeyInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ApiKeyInfoAdaptor implements SaveLiveApiKeyPort {

    private final ApiKeyInfoRepository apiKeyInfoRepository;
    private final ApikeyInfoMapper apikeyInfoMapper;

    @Override
    public void save(ApiKeyInfo apiKeyInfo) {
        apiKeyInfoRepository.save(apikeyInfoMapper.toEntity(apiKeyInfo));
    }

}
