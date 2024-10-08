package incerpay.paygate.infrastructure.internal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class StoreServiceCallerTest {

    private StoreServiceCaller storeServiceCaller;
    private StoreServiceApi storeServiceApi;

    @BeforeEach
    public void setUp() {
        // StoreServiceApi는 모킹
        storeServiceApi = mock(StoreServiceApi.class);
        // StoreServiceCaller는 모킹된 StoreServiceApi로 생성
        storeServiceCaller = new StoreServiceCaller(storeServiceApi);
    }

    @Test
    public void testVerifyPublicKey_withoutMatchLengthUnderSix() {

        // Given
        String invalidApiKey = "Bearer PublicKe";
        Long sellerId = 123L;

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            storeServiceCaller.verifyPublicKey(invalidApiKey, sellerId);
        });

        assertEquals("Non Api Key Accepted", exception.getMessage());
    }

    @Test
    public void testVerifyPublicKey_withMatchLengthOverSix() {

        // Given
        String invalidApiKey = "Bearer PublicKey1";
        Long sellerId = 123L;

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            storeServiceCaller.verifyPublicKey(invalidApiKey, sellerId);
        });

        assertEquals("Non Api Key Accepted", exception.getMessage());
    }

    @Test
    public void testVerifyPublicKey_withValidApiKey() {
        // Given
        String validApiKey = "Bearer PublicKey";
        Long sellerId = 123L;

        // When
        boolean result = storeServiceCaller.verifyPublicKey(validApiKey, sellerId);

        // Then
        assertTrue(result);
    }


    @Test
    public void testVerifyPublicKey_withNullApiKey() {
        // Given
        String nullApiKey = null;
        Long sellerId = 123L;

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            storeServiceCaller.verifyPublicKey(nullApiKey, sellerId);
        });

        assertEquals("Non Api Key Accepted", exception.getMessage());
    }

    @Test
    public void testVerifySecretKey() {
        // Given
        String apiKey = "Bearer SecretKey";
        Long sellerId = 123L;

        // When
        boolean result = storeServiceCaller.verifySecretKey(apiKey, sellerId);

        // Then
        assertTrue(result); // Always returns true
    }
}
