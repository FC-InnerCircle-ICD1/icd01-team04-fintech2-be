package com.incerpay.incerceller.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import static org.assertj.core.api.Assertions.*;

public class SellerTest {

    private Seller seller;
    private ApiKeyInfo validApiKey;
    private ApiKeyInfo invalidApiKey;

    @BeforeEach
    public void setUp() {
        // 유효한 ApiKeyInfo 객체 생성
        validApiKey = ApiKeyInfo.builder()
                .apiKey("valid-api-key")
                .apiKeyState(ApiKeyState.TEST)
                .build();

        // 유효하지 않은 ApiKeyInfo 객체 생성
        invalidApiKey = ApiKeyInfo.builder()
                .apiKey("invalid-api-key")
                .apiKeyState(ApiKeyState.LIVE)
                .build();

        // Seller 객체 생성
        seller = Seller.builder()
                .sellerId(1L)
                .sellerName("Test Seller")
                .apiKeyInfos(Arrays.asList(validApiKey))
                .build();
    }

    @Test
    public void 유효한_apikey_테스트() {
        assertThat(seller.hasApiKeyInfo(validApiKey)).isEqualTo(true);
    }

    @Test
    public void 유효하지않은_apikey_테스트() {
        assertThat(seller.hasApiKeyInfo(invalidApiKey)).isEqualTo(false);
    }

    @Test
    public void 빈_seller_테스트() {
        // 빈 apiKeyInfos 리스트로 테스트
        Seller emptySeller = Seller.builder()
                .sellerId(2L)
                .sellerName("Empty Seller")
                .apiKeyInfos(Collections.emptyList())
                .build();
        assertThat(emptySeller.hasApiKeyInfo(validApiKey)).isEqualTo(false);
    }

}
