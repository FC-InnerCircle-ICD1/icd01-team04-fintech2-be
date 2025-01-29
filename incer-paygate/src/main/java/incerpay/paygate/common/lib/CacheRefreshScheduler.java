package incerpay.paygate.common.lib;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Set;

@Component
@Slf4j
public class CacheRefreshScheduler {

    @Autowired
    @Qualifier("caffeineCacheManager")
    private CacheManager cacheManager;
    private final RedisTemplate<String, Object> redisTemplate;

    public CacheRefreshScheduler(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Scheduled(cron = "0 0 0 */25 * *")
    public void scheduledCacheRefresh() {
        log.info("Starting scheduled cache refresh");
        try {
            refreshTermsCache();
            refreshAllSellerCache();
        } catch (Exception e) {
            log.error("Failed to refresh cache", e);
        }
    }

    public void refreshSellerCache(Long sellerId) {
        String key = "seller::" + sellerId;
        log.info("Manually evicting cache for key: {}", key);

        redisTemplate.delete(key);

        log.info("Evicted cache for seller ID: {}", sellerId);
    }

    public void refreshAllSellerCache() {
        log.info("Refreshing all seller caches");

        Iterator<String> sellerIds = scanRedisForSellerIds("seller:*");

        while (sellerIds.hasNext()) {
            String sellerKey = sellerIds.next();
            String sellerId = sellerKey.replace("seller::", "");

            log.info("Processing seller with ID: {}", sellerId);
            refreshSellerCache(Long.parseLong(sellerId));
        }
    }

    public Iterator<String> scanRedisForSellerIds(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        log.info("Retrieved {} seller keys from Redis", keys.size());
        return keys.iterator();
    }

    public void refreshTermsCache() {

        CaffeineCache cache = (CaffeineCache) cacheManager.getCache("terms");
        if (cache != null) {
            cache.clear();
            log.info("Terms cache cleared successfully.");
        }

    }
}
