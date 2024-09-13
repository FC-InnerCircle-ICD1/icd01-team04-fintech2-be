package com.incerpay.incerceller.application.service;

import com.incerpay.incerceller.adapter.out.persistence.jpa.entity.ApiKeyInfoEntity;
import com.incerpay.incerceller.application.port.in.AssignApiKeyUseCase;
import com.incerpay.incerceller.application.port.out.SaveLiveApiKeyPort;
import com.incerpay.incerceller.application.port.out.SaveSellerPort;
import com.incerpay.incerceller.application.port.out.UpdateSellerPort;
import com.incerpay.incerceller.domain.ApiKeyInfo;
import com.incerpay.incerceller.domain.ApiKeyState;
import com.incerpay.incerceller.util.ApiKeyGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiKeyService implements AssignApiKeyUseCase {

	private final SaveLiveApiKeyPort saveLiveApiKeyPort;
	private final UpdateSellerPort updateSellerPort;

	@Override
	@Transactional
	public String assignApiKey(Long sellerId, ApiKeyState apiKeyState) {
		String apiKey = ApiKeyGenerator.generateUniqueKey(apiKeyState);

		ApiKeyInfo apiKeyInfo = ApiKeyInfo
				.builder()
				.apiKeyState(apiKeyState)
				.apiKey(apiKey)
				.build();

		saveLiveApiKeyPort.save(apiKeyInfo);

		updateSellerPort.updateSellerApiKey(sellerId, apiKeyInfo);

		return apiKey;
	}

}
