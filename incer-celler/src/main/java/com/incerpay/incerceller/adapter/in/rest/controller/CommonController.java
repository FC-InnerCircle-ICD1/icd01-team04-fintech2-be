package com.incerpay.incerceller.adapter.in.rest.controller;

import com.incerpay.incerceller.adapter.in.rest.docs.CommonControllerDocs;
import com.incerpay.incerceller.application.port.in.*;
import com.incerpay.incerceller.domain.Terms;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class CommonController implements CommonControllerDocs {

	private final GetTermsUseCase getTermsUseCase;

	@Override
	@GetMapping("/terms")
	public ResponseEntity<?> getPaymentTerms() {
		Terms terms = getTermsUseCase.getTerms();
		return ResponseEntity.ok(terms);
	}

}
