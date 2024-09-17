package com.incerpay.incerceller.application.port.in;

import com.incerpay.incerceller.application.dto.ConfirmApiKeyRequest;
import org.springframework.http.ResponseEntity;

public interface VerifyApiKeyUseCase {
    boolean vertifyApiKey(ConfirmApiKeyRequest confirmApiKeyRequest);

}
