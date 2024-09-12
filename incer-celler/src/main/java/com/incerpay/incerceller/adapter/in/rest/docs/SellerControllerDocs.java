package com.incerpay.incerceller.adapter.in.rest.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface SellerControllerDocs {

	@Operation(summary = "단건 상점 조회", description = "상점 ID로 상점을 조회합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "성공"),
			@ApiResponse(responseCode = "500", description = "실패")})
	ResponseEntity<?> getSeller(@PathVariable Long sellerId);

	@Operation(summary = "여러개 상점 조회", description = "어드민 ID로 상점을 조회합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "성공"),
			@ApiResponse(responseCode = "500", description = "실패")})
	ResponseEntity<?> getSellers(@PathVariable Long userId);

}
