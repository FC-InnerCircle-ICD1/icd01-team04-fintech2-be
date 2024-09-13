package com.incerpay.incerceller.adapter.in.rest.controller;

import com.incerpay.incerceller.adapter.in.rest.docs.ApiKeyControllerDocs;
import com.incerpay.incerceller.application.port.in.AssignApiKeyUseCase;
import com.incerpay.incerceller.domain.ApiKeyState;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apikey")
@RequiredArgsConstructor
public class ApiKeyController implements ApiKeyControllerDocs {

	private final AssignApiKeyUseCase assignApiKeyUseCase;

	@Override
	@GetMapping
	public ResponseEntity<?> assignApiKey(@RequestParam Long sellerId, @RequestParam ApiKeyState apiKeyState) {
		return ResponseEntity.ok(assignApiKeyUseCase.assignApiKey(sellerId, apiKeyState));
	}

}
