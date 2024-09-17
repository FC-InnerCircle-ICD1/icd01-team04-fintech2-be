package com.incerpay.incerceller.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Seller {
	private Long sellerId;
	private String sellerName;
	private List<ApiKeyInfo> apiKeyInfos;

	public boolean hasApiKeyInfo(ApiKeyInfo targetApiKey) {
		return this.apiKeyInfos.stream()
				.anyMatch(apiKeyInfo -> apiKeyInfo.isApiKeyInfo(targetApiKey));
	}


}
