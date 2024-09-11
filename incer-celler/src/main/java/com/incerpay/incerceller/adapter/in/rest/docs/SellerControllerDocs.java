package com.incerpay.incerceller.adapter.in.rest.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface SellerControllerDocs {

	@Operation(summary = "API 키 생성", description = "테스트, 라이브 키를 생성합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "생성성공"),
			@ApiResponse(responseCode = "500", description = "생성실패")})
	ResponseEntity<?> getSeller(@RequestParam Long sellerId);

}
