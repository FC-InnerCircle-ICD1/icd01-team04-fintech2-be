package com.incerpay.incerceller.adapter.in.rest.controller;

import com.incerpay.incerceller.adapter.in.rest.docs.SellerControllerDocs;
import com.incerpay.incerceller.application.port.in.AssignSellerUseCase;
import com.incerpay.incerceller.application.port.in.GetSellerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerController implements SellerControllerDocs {

	private final GetSellerUseCase getSellerUseCase;
	private final AssignSellerUseCase assignSellerUseCase;

	@Override
	@GetMapping("/{sellerId}")
	public ResponseEntity<?> getSeller(@PathVariable Long sellerId) {
		return ResponseEntity.ok(getSellerUseCase.getSeller(sellerId));
	}

	@Override
	@PostMapping
	public ResponseEntity<?> assignSeller(@RequestParam Long customerId, @RequestParam String sellerName) {
		assignSellerUseCase.assignSeller(customerId, sellerName);
		return ResponseEntity.ok("등록 성공");
	}

	// todo : 상점 수정 기능

}
