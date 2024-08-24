package com.incerpay.incerceller.common.util;

import java.util.UUID;

public class ApiKeyGenerator {
    private static final String PREFIX = "test_";
    // 고유한 테스트 키를 발급하는 메서드
    public static String generateUniqueTestKey() {
        // UUID를 기반으로 무작위 값을 생성하고, 접두사와 결합
        return PREFIX + UUID.randomUUID().toString().replace("-", "");
    }

}
