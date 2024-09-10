package com.incerpay.incerceller.mapper;

import com.incerpay.incerceller.adapter.out.persistence.jpa.entity.ApiKeyInfoEntity;
import com.incerpay.incerceller.domain.ApiKeyInfo;
import org.springframework.stereotype.Component;

@Component
public class ApikeyInfoMapper
{
	public ApiKeyInfoEntity toEntity(ApiKeyInfo apiKeyInfo) {
		return ApiKeyInfoEntity.builder()
				.apiKey(apiKeyInfo.getApiKey())
				.apiKeyState(apiKeyInfo.getApiKeyState())
				.build();
	}

	public ApiKeyInfo toDomain(ApiKeyInfoEntity apiKeyInfoEntity) {
		return ApiKeyInfo.builder()
				.apiKey(apiKeyInfoEntity.getApiKey())
				.apiKeyState(apiKeyInfoEntity.getApiKeyState())
				.build();
	}

}
