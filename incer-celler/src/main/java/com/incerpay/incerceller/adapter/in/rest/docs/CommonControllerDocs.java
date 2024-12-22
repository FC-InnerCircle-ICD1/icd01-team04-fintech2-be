package com.incerpay.incerceller.adapter.in.rest.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface CommonControllerDocs {

	@Operation(summary = "결제 약관 조회", description = "현재 시점의 결제 약관을 조회합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "성공"),
			@ApiResponse(responseCode = "500", description = "실패")})
	ResponseEntity<?> getPaymentTerms();

}
