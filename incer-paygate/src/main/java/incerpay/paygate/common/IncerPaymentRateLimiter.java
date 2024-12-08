package incerpay.paygate.common;

import incerpay.paygate.common.exception.IncerPayRateLimitException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class IncerPaymentRateLimiter {
    private final RedisTemplate<String, Object> redisTemplate;

    private static final int BURST_EXPIRE = 5;
    private static final int BURST_LIMIT = 50;
    private static final int WINDOW_EXPIRE = 5;
    private static final int WINDOW_LIMIT = 1000;
    private static final int BLOCK_EXPIRE_HOURS = 24;

    public boolean confirmAllowed(String ip) {
        try {

            String burstKey  = "burst:" + ip;
            String windowKey = "window:" + ip;
            String blockKey  = "blocked:" + ip;

            if (isIpBlocked(blockKey)) {
                throw new IncerPayRateLimitException("시도 횟수 초과");
            }

            Long burstCount  = incrementAndSetExpiry(burstKey, BURST_EXPIRE, TimeUnit.SECONDS, ip, "burst");
            Long windowCount = incrementAndSetExpiry(windowKey, WINDOW_EXPIRE, TimeUnit.MINUTES, ip, "window");

            setLoggingByRate(burstCount, windowCount);

            boolean isAllowed = confirmInvalidIp(blockKey, burstCount, windowCount, ip);

            if (!isAllowed) {
                throw new IncerPayRateLimitException("시도 횟수 초과");
            }

            return true;

        } catch (RedisConnectionFailureException e) {
            log.error("Redis connection failed while incrementing: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            log.error("Rate limiting failed for IP: {}", ip, e);
            return false;
        }
    }


    private boolean confirmInvalidIp(String blockKey, Long burstCount, Long windowCount, String ip) {

        log.warn("blockKey {} , Burst count: {}, Window count: {}", blockKey, burstCount, windowCount);

        if (isLimitExceeded(burstCount, windowCount)) {
            redisTemplate.opsForValue().set(blockKey, "true", BLOCK_EXPIRE_HOURS, TimeUnit.HOURS);
            log.warn("IP blocked: {}", ip);
            return false;
        }

        return true;
    }

    private boolean isIpBlocked(String ipKey) {
        return "true".equals(redisTemplate.opsForValue().get(ipKey));
    }

    private void setLoggingByRate(Long burstCount, Long windowCount) {

        if (burstCount % 10 == 0) {
            log.info("Burst count: {}", burstCount);
        }

        if (windowCount % 100 == 0) {
            log.info("Window count: {}", windowCount);
        }
    }

    private boolean isLimitExceeded(long burstCount, long windowCount) {
        return burstCount > BURST_LIMIT || windowCount > WINDOW_LIMIT;
    }


    private Long incrementAndSetExpiry(String key, int expiryTime, TimeUnit timeUnit, String ip, String type) {
        Long count = redisTemplate.opsForValue().increment(key);

        log.info("Incremented key: {} , count: {}", key, count);

        if (count == null) {
            log.warn("Cannot found a increment {} count for IP: {}, count: {}", type, ip, count);
            return 0L;
        }

        if (count == 1L) {
            redisTemplate.expire(key, expiryTime, timeUnit);
        }

        return count;
    }

    public IncerPaymentRateLimiter(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}