package incerpay.payment.domain.component;

import incerpay.payment.common.dto.PaymentQuoteCommand;
import incerpay.payment.common.lib.clock.ClockManager;
import incerpay.payment.domain.entity.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;

@RequiredArgsConstructor
@Component
public class PaymentFactory {
    private final ClockManager clockManager;
    public Payment create(PaymentQuoteCommand command) {
        Clock clock = clockManager.getClock();
        Long amount = command.amount();
        Instant createdAt = clock.instant();
        return Payment.of(
                command.sellerId(),
                amount,
                createdAt
        );
    }

}
