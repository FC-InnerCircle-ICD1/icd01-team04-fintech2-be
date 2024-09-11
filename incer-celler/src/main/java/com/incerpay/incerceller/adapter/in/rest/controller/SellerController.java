package com.incerpay.incerceller.adapter.in.rest.controller;

import com.incerpay.incerceller.adapter.in.rest.docs.SellerControllerDocs;
import com.incerpay.incerceller.application.port.in.ApproveSellerUseCase;
import com.incerpay.incerceller.application.port.in.GetSellerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apikey")
@RequiredArgsConstructor
public class SellerController implements SellerControllerDocs {

	private final GetSellerUseCase getSellerUseCase;
	private final ApproveSellerUseCase approveSellerUseCase;

	@GetMapping
	public ResponseEntity<?> getSeller(@RequestParam Long sellerId) {
		return ResponseEntity.ok(getSellerUseCase.getSeller(sellerId));
	}

}
