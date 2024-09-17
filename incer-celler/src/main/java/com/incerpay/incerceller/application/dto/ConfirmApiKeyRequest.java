package com.incerpay.incerceller.application.dto;

import com.incerpay.incerceller.domain.ApiKeyInfo;

//todo : @Valid 추가
public record ConfirmApiKeyRequest(Long sellerId, ApiKeyInfo apiKeyInfo) {

}
