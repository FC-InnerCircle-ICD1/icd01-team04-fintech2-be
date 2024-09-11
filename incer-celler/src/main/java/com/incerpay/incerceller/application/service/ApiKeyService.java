package com.incerpay.incerceller.application.service;

import com.incerpay.incerceller.adapter.out.persistence.jpa.entity.ApiKeyInfoEntity;
import com.incerpay.incerceller.application.port.in.AssignApiKeyUseCase;
import com.incerpay.incerceller.application.port.out.SaveLiveApiKeyPort;
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

	@Override
	@Transactional
	public String assignApiKey(String sellerId, ApiKeyState apiKeyState) {
		String apiKey = ApiKeyGenerator.generateUniqueKey(apiKeyState);
		saveLiveApiKeyPort.save(new ApiKeyInfoEntity(apiKey, apiKeyState));
		return apiKey;
	}

}
