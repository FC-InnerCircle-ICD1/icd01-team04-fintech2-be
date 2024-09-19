package incerpay.payment.common.dto;

import incerpay.payment.common.lib.request.RequestParameterException;
import org.javamoney.moneta.Money;

import java.time.LocalDateTime;


public record PaymentQuoteCommand(
        String sellerId,
        Long amount,
        LocalDateTime expiredAt

){
    public PaymentQuoteCommand {
        Money _amount = Money.of(amount, "KRW");
        if (_amount.isNegativeOrZero()) {
            throw new RequestParameterException("Amount must be positive");
        }
    }
}
