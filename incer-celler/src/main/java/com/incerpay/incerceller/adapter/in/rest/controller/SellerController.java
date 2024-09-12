package com.incerpay.incerceller.adapter.in.rest.controller;

import com.incerpay.incerceller.adapter.in.rest.docs.SellerControllerDocs;
import com.incerpay.incerceller.application.port.in.ApproveSellerUseCase;
import com.incerpay.incerceller.application.port.in.GetSellerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerController implements SellerControllerDocs {

	private final GetSellerUseCase getSellerUseCase;
	private final ApproveSellerUseCase approveSellerUseCase;

	@GetMapping("/{sellerId}")
	public ResponseEntity<?> getSeller(@PathVariable Long sellerId) {
		return ResponseEntity.ok(getSellerUseCase.getSeller(sellerId));
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getSellers(@PathVariable Long userId) {
		return ResponseEntity.ok(getSellerUseCase.getSellers(userId));
	}

}
