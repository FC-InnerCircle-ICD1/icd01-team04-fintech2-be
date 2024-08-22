package incerpay.payment.domain.dto;

import incerpay.payment.domain.Payment;
import incerpay.payment.domain.vo.PaymentProperty;
import incerpay.payment.domain.vo.PaymentStatus;
import org.javamoney.moneta.Money;

import java.time.Clock;
import java.time.LocalDateTime;


public record PayCommand (
        Money amount,
        LocalDateTime expiredAt

){
    public Payment toEntity(Clock clock) {
        return Payment.builder()
                .paymentProperty(PaymentProperty.builder()
                        .amount(amount)
                        .status(PaymentStatus.PENDING)
                        .build()
                )
                .createdAt(clock.instant())
                .expiredAt(clock.instant().plusSeconds(5*60))
                .build();
    }
}
