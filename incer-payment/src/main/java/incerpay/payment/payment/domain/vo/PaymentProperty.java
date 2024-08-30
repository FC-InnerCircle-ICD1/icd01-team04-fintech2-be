package incerpay.payment.payment.domain.vo;

import incerpay.payment.common.lib.money.MoneyConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.javamoney.moneta.Money;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class PaymentProperty {
    @Convert(converter = MoneyConverter.class)
    private Money amount;
}
