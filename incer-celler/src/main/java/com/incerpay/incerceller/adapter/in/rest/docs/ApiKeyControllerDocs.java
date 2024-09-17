package com.incerpay.incerceller.adapter.in.rest.docs;

import com.incerpay.incerceller.application.dto.AssignApiKeyRequest;
import com.incerpay.incerceller.application.dto.ConfirmApiKeyRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface ApiKeyControllerDocs {
	@Operation(summary = "API 키 검증", description = "테스트, 라이브 키를 검증합니다")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "검증성공"),
			@ApiResponse(responseCode = "500", description = "검증실패")})
	ResponseEntity<?> confirmApiKey(@Schema(description = "상점ID와 key정보") @RequestBody ConfirmApiKeyRequest confirmApiKeyRequest);

	@Operation(summary = "API 키 생성", description = "테스트, 라이브 키를 생성합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "생성성공"),
			@ApiResponse(responseCode = "500", description = "생성실패")})
	ResponseEntity<?> assignApiKey(@Schema(description = "상점ID와 key정보") @RequestBody AssignApiKeyRequest assignApiKeyRequest);

}
