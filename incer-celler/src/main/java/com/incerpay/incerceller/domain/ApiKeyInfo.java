package com.incerpay.incerceller.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class ApiKeyInfo {
	private String apiKey;
	private ApiKeyState apiKeyState;
//	private Long version;

	public boolean isApiKeyInfo(ApiKeyInfo targetApiKeyInfo) {
		return this.apiKey.equals(targetApiKeyInfo.apiKey) && this.apiKeyState.equals(targetApiKeyInfo.apiKeyState);
	}

}
