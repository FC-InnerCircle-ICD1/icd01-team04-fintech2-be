package com.incerpay.incerceller.adapter.out.persistence;

import com.incerpay.incerceller.adapter.out.persistence.jpa.repository.ApiKeyInfoRepository;
import com.incerpay.incerceller.application.port.out.SaveApiKeyPort;
import com.incerpay.incerceller.application.port.out.SelectApiKeyPort;
import com.incerpay.incerceller.domain.ApiKeyInfo;
import com.incerpay.incerceller.mapper.ApikeyInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ApiKeyInfoAdaptor implements SaveApiKeyPort, SelectApiKeyPort {

    private final ApiKeyInfoRepository apiKeyInfoRepository;
    private final ApikeyInfoMapper apikeyInfoMapper;

    @Override
    public void save(ApiKeyInfo apiKeyInfo) {
        apiKeyInfoRepository.save(apikeyInfoMapper.toEntity(apiKeyInfo));
    }

    @Override
    public ApiKeyInfo selectApiKey(String apiKey) {
        return apikeyInfoMapper.toDomain(apiKeyInfoRepository.findById(apiKey)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 키 입니다.")));
    }

}
