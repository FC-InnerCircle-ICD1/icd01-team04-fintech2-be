package com.incerpay.incerceller.application.dto;

/**
 * 상점 등록 요청서
 */
public record SellerRegisterRequest(Long userId, String sellerName, String accountNumber, String businessId, boolean businessStatus) {

}
