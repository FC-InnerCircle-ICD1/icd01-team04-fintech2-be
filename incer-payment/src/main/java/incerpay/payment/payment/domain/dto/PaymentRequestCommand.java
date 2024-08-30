package incerpay.payment.payment.domain.dto;

import incerpay.payment.common.lib.request.RequestParameterException;
import incerpay.payment.payment.domain.entity.Payment;
import incerpay.payment.payment.domain.vo.PaymentProperty;
import incerpay.payment.payment.domain.vo.PaymentState;
import org.javamoney.moneta.Money;

import java.time.Clock;
import java.time.LocalDateTime;


public record PaymentRequestCommand(
        // buyer 정보
        String sellerId,
        Money amount,
        LocalDateTime expiredAt

){
    public PaymentRequestCommand {
        if (amount.isNegativeOrZero()) {
            throw new RequestParameterException("Amount must be positive");
        }
    }

    public Payment toEntity(Clock clock) {
        return Payment.builder()
                .paymentProperty(PaymentProperty.builder()
                        .amount(amount)
                        .build()
                )
                .sellerId(sellerId)
                .state(PaymentState.PENDING)
                .createdAt(clock.instant())
                .expiredAt(clock.instant().plusSeconds(5*60))
                .build();
    }
}
