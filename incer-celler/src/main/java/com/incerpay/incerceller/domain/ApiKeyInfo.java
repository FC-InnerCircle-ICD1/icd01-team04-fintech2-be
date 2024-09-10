package com.incerpay.incerceller.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApiKeyInfo {
	private String apiKey;
	private String sellerId;
	private ApiKeyState apiKeyState;
	private Long version;
}
