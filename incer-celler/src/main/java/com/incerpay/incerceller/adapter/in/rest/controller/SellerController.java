package com.incerpay.incerceller.adapter.in.rest.controller;

import com.incerpay.incerceller.adapter.in.rest.docs.SellerControllerDocs;
import com.incerpay.incerceller.application.dto.CardRegisterRequest;
import com.incerpay.incerceller.application.port.in.AssignCardUseCase;
import com.incerpay.incerceller.application.port.in.AssignSellerUseCase;
import com.incerpay.incerceller.application.port.in.GetSellerUseCase;
import com.incerpay.incerceller.domain.Seller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerController implements SellerControllerDocs {

	private final GetSellerUseCase getSellerUseCase;
	private final AssignSellerUseCase assignSellerUseCase;
	private final AssignCardUseCase assignCardUseCase;

	@Override
	@GetMapping("/{sellerId}")
	public ResponseEntity<?> getSeller(@PathVariable Long sellerId) {
		Seller seller = getSellerUseCase.getSeller(sellerId);
		return ResponseEntity.ok(seller);
	}

	@Override
	@GetMapping
	public ResponseEntity<?> assignSeller(@RequestParam Long sellerId, @RequestParam String sellerName) {
		assignSellerUseCase.assignSeller(sellerId, sellerName);
		return ResponseEntity.ok("등록 성공");
	}

	@Override
	@PostMapping("/card")
	public ResponseEntity<?> assignCard(@RequestBody @Valid CardRegisterRequest cardRegisterRequest) {
		assignCardUseCase.assignCard(cardRegisterRequest);
		return ResponseEntity.ok("등록 성공");
	}

}
