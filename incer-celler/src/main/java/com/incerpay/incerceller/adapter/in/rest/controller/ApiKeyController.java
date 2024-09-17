package com.incerpay.incerceller.adapter.in.rest.controller;

import com.incerpay.incerceller.adapter.in.rest.docs.ApiKeyControllerDocs;
import com.incerpay.incerceller.application.dto.AssignApiKeyRequest;
import com.incerpay.incerceller.application.dto.ConfirmApiKeyRequest;
import com.incerpay.incerceller.application.port.in.AssignApiKeyUseCase;
import com.incerpay.incerceller.application.port.in.VerifyApiKeyUseCase;
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
	public ResponseEntity<?> confirmApiKey(ConfirmApiKeyRequest confirmApiKeyRequest) {
		return ResponseEntity.ok(verifyApiKeyUseCase.vertifyApiKey(confirmApiKeyRequest));
	}
	@Override
	@PostMapping
	public ResponseEntity<?> assignApiKey(@RequestBody AssignApiKeyRequest assignApiKeyRequest) {
		return ResponseEntity.ok(assignApiKeyUseCase.assignApiKey(assignApiKeyRequest));
	}

}
