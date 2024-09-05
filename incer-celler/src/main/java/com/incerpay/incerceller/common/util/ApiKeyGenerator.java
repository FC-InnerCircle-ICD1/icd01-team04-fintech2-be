package com.incerpay.incerceller.common.util;

import com.incerpay.incerceller.domain.ApiKeyState;

import java.util.UUID;

public class ApiKeyGenerator {
    private static final String PREFIX_TEST = "test_";
    private static final String PREFIX_LIVE = "live_";
    // 고유한 테스트 키를 발급하는 메서드
    public static String generateUniqueKey(ApiKeyState apiKeyState) {
        if(apiKeyState.equals(ApiKeyState.TEST))
            return PREFIX_TEST + UUID.randomUUID().toString().replace("-", "");
        return PREFIX_LIVE + UUID.randomUUID().toString().replace("-", "");
    }

}
