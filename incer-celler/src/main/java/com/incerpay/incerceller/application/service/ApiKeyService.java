package com.incerpay.incerceller.application.service;

import com.incerpay.incerceller.adapter.out.persistence.jpa.entity.ApiKeyInfoEntity;
import com.incerpay.incerceller.application.port.in.AssignApiKeyUseCase;
import com.incerpay.incerceller.application.port.in.FindSellerUseCase;
import com.incerpay.incerceller.application.port.out.SaveLiveApiKeyPort;
import com.incerpay.incerceller.application.port.out.SelectSellerPort;
import com.incerpay.incerceller.util.ApiKeyGenerator;
import com.incerpay.incerceller.domain.ApiKeyState;
import com.incerpay.incerceller.domain.Seller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiKeyService implements AssignApiKeyUseCase, FindSellerUseCase {

	private final SaveLiveApiKeyPort saveLiveApiKeyPort;
	private final SelectSellerPort selectSellerPort;

	@Override
	@Transactional
	public String assignApiKey(String sellerId, ApiKeyState apiKeyState) {
		String apiKey = ApiKeyGenerator.generateUniqueKey(apiKeyState);
		saveLiveApiKeyPort.save(new ApiKeyInfoEntity(apiKey, apiKeyState));
		return apiKey;
	}

	@Override
	public List<Seller> findSellers(Long adminId) {
		//todo :validator 추가
		return selectSellerPort.selectSellers(adminId);
	}

	@Override
	public Seller findSeller(Long sellerId) {
		return selectSellerPort.selectSeller(sellerId);
	}


}
