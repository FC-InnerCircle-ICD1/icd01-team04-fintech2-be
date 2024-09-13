package com.incerpay.incerceller.adapter.in.rest.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface SellerControllerDocs {

	@Operation(summary = "단건 상점 조회", description = "상점 ID로 상점을 조회합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "성공"),
			@ApiResponse(responseCode = "500", description = "실패")})
	ResponseEntity<?> getSeller(@PathVariable Long sellerId);

	@Operation(summary = "상점 등록", description = "상점을 등록합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "성공"),
			@ApiResponse(responseCode = "500", description = "실패")})
	ResponseEntity<?> assignSeller(@RequestParam Long customerId, @RequestParam String sellerName);

}
