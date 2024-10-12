package com.incerpay.incerceller.application.service;

import com.incerpay.incerceller.application.dto.AssignApiKeyRequest;
import com.incerpay.incerceller.application.port.in.AssignApiKeyUseCase;
import com.incerpay.incerceller.application.port.in.GetSellerUseCase;
import com.incerpay.incerceller.application.port.in.VerifyApiKeyUseCase;
import com.incerpay.incerceller.application.port.out.SaveApiKeyPort;
import com.incerpay.incerceller.application.port.out.UpdateSellerPort;
import com.incerpay.incerceller.domain.ApiKeyInfo;
import com.incerpay.incerceller.domain.Seller;
import com.incerpay.incerceller.util.ApiKeyGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiKeyService implements AssignApiKeyUseCase, VerifyApiKeyUseCase {

	private final SaveApiKeyPort saveApiKeyPort;
	private final UpdateSellerPort updateSellerPort;
	private final GetSellerUseCase getSellerUseCase;

	@Override
	@Transactional
	public String assignApiKey(AssignApiKeyRequest assignApiKeyRequest) {
		String apiKey = ApiKeyGenerator.generateUniqueKey(assignApiKeyRequest.apiKeyState());

		ApiKeyInfo apiKeyInfo = ApiKeyInfo
				.builder()
				.apiKeyState(assignApiKeyRequest.apiKeyState())
				.apiKey(apiKey)
				.build();

		saveApiKeyPort.save(apiKeyInfo);

		updateSellerPort.updateSellerApiKey(assignApiKeyRequest.sellerId(), apiKeyInfo);

		return apiKey;
	}

	@Override
	public boolean vertifyApiKey(Long sellerId, ApiKeyInfo apiKeyInfo) {
		Seller seller = getSellerUseCase.getSeller(sellerId);
		return seller.hasApiKeyInfo(apiKeyInfo);
		// 포트의 기능은 단순 데이터 가져오는 역할만 하는가? 검증하는 기능은 어디들어가야할까?
	}

}
