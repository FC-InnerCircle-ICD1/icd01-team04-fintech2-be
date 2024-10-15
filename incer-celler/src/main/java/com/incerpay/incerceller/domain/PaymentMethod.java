package com.incerpay.incerceller.domain;

public enum PaymentMethod {
    CREDIT_CARD("신용/체크 카드"),
    NAVER("네이버"),
    KAKAO("카카오"),
    TOSS("토스"),
    PAYCO("페이코");

    PaymentMethod(String name) {
    }

}
