package com.incerpay.incerceller.application.service;

import com.incerpay.incerceller.adapter.out.persistence.ApiKeyInfoAdaptor;
import com.incerpay.incerceller.adapter.out.persistence.ApiKeyInfoEntity;
import com.incerpay.incerceller.application.port.in.AssignApiKeyUseCase;
import com.incerpay.incerceller.common.util.ApiKeyGenerator;
import com.incerpay.incerceller.domain.ApiKeyState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiKeyService implements AssignApiKeyUseCase {

	private final ApiKeyInfoAdaptor apiKeyInfoAdaptor;

	@Override
	public String assignApiKey(String sellerId, ApiKeyState apiKeyState) {
		String apiKey = ApiKeyGenerator.generateUniqueKey(apiKeyState);
		// todo : 어댑터에 도메인으로 넘기고, 도메인 -> 엔티티 변환과정은 어디에서 하는가?
		apiKeyInfoAdaptor.save(new ApiKeyInfoEntity(apiKey, sellerId, apiKeyState));
		return apiKey;
	}

}
