package com.incerpay.incerceller.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class Seller {
	private Long sellerId;
	private String sellerName;
	private List<ApiKeyInfo> apiKeyInfos;
	private List<PaymentMethod> paymentMethods;
	private List<CardCompany> cardCompanies;

	public boolean hasApiKeyInfo(ApiKeyInfo targetApiKey) {
		return this.apiKeyInfos.stream()
				.anyMatch(apiKeyInfo -> apiKeyInfo.isApiKeyInfo(targetApiKey));
	}


}
