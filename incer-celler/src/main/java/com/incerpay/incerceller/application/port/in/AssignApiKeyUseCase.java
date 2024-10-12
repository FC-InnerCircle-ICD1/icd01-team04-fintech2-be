package com.incerpay.incerceller.application.port.in;

import com.incerpay.incerceller.application.dto.AssignApiKeyRequest;

public interface AssignApiKeyUseCase {
    String assignApiKey(AssignApiKeyRequest assignApiKeyRequest);
}
