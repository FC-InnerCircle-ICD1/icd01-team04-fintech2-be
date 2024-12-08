package incerpay.paygate.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IncerPaymentRateLimiterTest {

    private static final long BURST_LIMIT = 50;
    private static final long WINDOW_LIMIT = 2000;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private IncerPaymentRateLimiter rateLimiter;

    @BeforeEach
    public void setUp() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    @DisplayName("정상적인 요청은 허용되어야 한다.")
    public void shouldAllowNormalRequest() {
        // given
        String testIp = "192.168.1.1";
        when(valueOperations.increment(anyString())).thenReturn(1L);

        // when
        when(valueOperations.increment("burst:" + testIp)).thenReturn(BURST_LIMIT);
        when(valueOperations.increment("window:" + testIp)).thenReturn(WINDOW_LIMIT);

        // then
        assertDoesNotThrow(() -> rateLimiter.confirmAllowed(testIp));
        verify(valueOperations, times(2)).increment(anyString());
    }

    @Test
    @DisplayName("burst 제한을 초과하면 요청이 거부되어야 한다.")
    public void shouldRejectWhenBurstLimitExceeded() {
        // given
        String testIp = "192.168.1.1";

        // when
        when(valueOperations.increment("burst:" + testIp)).thenReturn(BURST_LIMIT + 1);
        when(valueOperations.increment("window:" + testIp)).thenReturn(WINDOW_LIMIT);

        // then
        assertThrows(RuntimeException.class, () -> rateLimiter.confirmAllowed(testIp));
    }

    @Test
    @DisplayName("window 제한을 초과하면 요청이 거부되어야 한다.")
    public void shouldRejectWhenWindowLimitExceeded() {
        // given
        String testIp = "192.168.1.1";

        // when
        when(valueOperations.increment("burst:" + testIp)).thenReturn(BURST_LIMIT);
        when(valueOperations.increment("window:" + testIp)).thenReturn(WINDOW_LIMIT + 1);

        // then
        assertThrows(RuntimeException.class, () -> rateLimiter.confirmAllowed(testIp));
    }

    @Test
    @DisplayName("Redis 실패 시 요청을 허용해야 한다.")
    public void shouldAllowRequestWhenRedisFails() {
        // given
        String testIp = "192.168.1.1";
        when(valueOperations.increment(anyString())).thenReturn(null);

        // when & then
        assertDoesNotThrow(() -> rateLimiter.confirmAllowed(testIp));
    }
}
