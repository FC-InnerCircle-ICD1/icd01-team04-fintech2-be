package com.incerpay.incerceller.adapter.in.rest.controller;

import com.incerpay.incerceller.adapter.in.rest.docs.SellerControllerDocs;
import com.incerpay.incerceller.application.dto.CardRegisterRequest;
import com.incerpay.incerceller.application.port.in.AssignSellerUseCase;
import com.incerpay.incerceller.application.port.in.GetSellerUseCase;
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

	@Override
	@GetMapping("/{sellerId}")
	public ResponseEntity<?> getSeller(@RequestParam(name = "sellerId") Long sellerId) {
		return ResponseEntity.ok(getSellerUseCase.getSeller(sellerId));
	}

	@Override
	@GetMapping
	public ResponseEntity<?> assignSeller(@RequestParam(name = "sellerId") Long sellerId, @RequestParam(name = "sellerName") String sellerName) {
		assignSellerUseCase.assignSeller(sellerId, sellerName);
		return ResponseEntity.ok("등록 성공");
	}

	@Override
	@PostMapping("/card")
	// todo : String으로 받고 enum 변환하는게 날듯?
	public ResponseEntity<?> assignCard(@Valid CardRegisterRequest cardRegisterRequest) {
		return ResponseEntity.ok("등록 성공");
	}

}
