package incerpay.paygate.common.config;

import lombok.extern.slf4j.Slf4j;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Slf4j
@Configuration
public class PaymentResilienceConfig {

    private static final String CIRCUIT_BREAKER_NAME = "paymentCircuitBreaker";
    private static final String RETRY_NAME = "paymentRetry";

    @Bean
    public CircuitBreaker paymentCircuitBreaker() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(60))
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(10)
                .minimumNumberOfCalls(5)
                .permittedNumberOfCallsInHalfOpenState(3)
                .build();

        CircuitBreaker circuitBreaker = CircuitBreaker.of(CIRCUIT_BREAKER_NAME, config);

        // 서킷브레이커 이벤트 로깅 설정
        circuitBreaker.getEventPublisher()
                .onStateTransition(event -> log.info("[{}] State changed from {} to {}",
                        CIRCUIT_BREAKER_NAME,
                        event.getStateTransition().getFromState(),
                        event.getStateTransition().getToState()))
                .onSuccess(event -> log.debug("[{}] Call succeeded in {}ms",
                        CIRCUIT_BREAKER_NAME,
                        event.getElapsedDuration().toMillis()))
                .onError(event -> log.warn("[{}] Call failed in {}ms: {}",
                        CIRCUIT_BREAKER_NAME,
                        event.getElapsedDuration().toMillis(),
                        event.getThrowable().getMessage()));

        return circuitBreaker;
    }

    @Bean
    public Retry paymentRetry() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofSeconds(1))
                .retryExceptions(RuntimeException.class)
                .build();

        Retry retry = Retry.of(RETRY_NAME, config);

        // 리트라이 이벤트 로깅 설정
        retry.getEventPublisher()
                .onRetry(event -> log.info("[{}] Attempt #{}/{}",
                        RETRY_NAME,
                        event.getNumberOfRetryAttempts(),
                        config.getMaxAttempts()))
                .onSuccess(event -> log.info("[{}] Succeeded after {} attempt(s)",
                        RETRY_NAME,
                        event.getNumberOfRetryAttempts()))
                .onError(event -> log.error("[{}] Failed after {} attempt(s): {}",
                        RETRY_NAME,
                        event.getNumberOfRetryAttempts(),
                        event.getLastThrowable().getMessage()));

        return retry;
    }
}