package com.incerpay.incerceller.application.dto;

import com.incerpay.incerceller.domain.ApiKeyState;

public record AssignApiKeyRequest(Long sellerId, ApiKeyState apiKeyState) {

}
