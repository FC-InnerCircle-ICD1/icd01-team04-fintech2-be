package incerpay.paygate.domain.component;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Component
public class ResilienceWrapper {
    private final CircuitBreaker circuitBreaker;
    private final Retry retry;
    private final Bulkhead bulkhead;

    public ResilienceWrapper(
            @Qualifier("paymentCircuitBreaker") CircuitBreaker circuitBreaker,
            @Qualifier("paymentRetry") Retry retry,
            @Qualifier("paymentBulkhead") Bulkhead bulkhead
    ) {
        this.circuitBreaker = circuitBreaker;
        this.retry = retry;
        this.bulkhead = bulkhead;
    }

    public <T> T execute(Supplier<T> supplier) {
        return Retry.decorateSupplier(retry,
                        CircuitBreaker.decorateSupplier(circuitBreaker,
                                Bulkhead.decorateSupplier(bulkhead, supplier)))
                .get();
    }

    public <T> CompletableFuture<T> executeAsync(Supplier<T> supplier) {
        Supplier<T> decoratedSupplier = Retry.decorateSupplier(retry,
                CircuitBreaker.decorateSupplier(circuitBreaker,
                        Bulkhead.decorateSupplier(bulkhead, supplier)));

        return CompletableFuture.supplyAsync(decoratedSupplier);
    }
}