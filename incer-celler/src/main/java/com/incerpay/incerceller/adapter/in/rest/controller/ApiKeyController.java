package com.incerpay.incerceller.adapter.in.rest.controller;

import com.incerpay.incerceller.adapter.in.rest.docs.ApiKeyControllerDocs;
import com.incerpay.incerceller.application.dto.AssignApiKeyRequest;
import com.incerpay.incerceller.application.port.in.AssignApiKeyUseCase;
import com.incerpay.incerceller.application.port.in.VerifyApiKeyUseCase;
import com.incerpay.incerceller.domain.ApiKeyInfo;
import com.incerpay.incerceller.domain.ApiKeyState;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apikey")
@RequiredArgsConstructor
public class ApiKeyController implements ApiKeyControllerDocs {

	private final AssignApiKeyUseCase assignApiKeyUseCase;
	private final VerifyApiKeyUseCase verifyApiKeyUseCase;

	@Override
	@GetMapping
	public ResponseEntity<?> confirmApiKey(@RequestParam Long sellerId, @RequestParam String apiKey, @RequestParam ApiKeyState apiKeyState) {
		return ResponseEntity.ok(verifyApiKeyUseCase.vertifyApiKey(sellerId, new ApiKeyInfo(apiKey, apiKeyState)));
	}

	@Override
	@PostMapping
	public ResponseEntity<?> assignApiKey(@RequestBody AssignApiKeyRequest assignApiKeyRequest) {
		return ResponseEntity.ok(assignApiKeyUseCase.assignApiKey(assignApiKeyRequest));
	}

}
