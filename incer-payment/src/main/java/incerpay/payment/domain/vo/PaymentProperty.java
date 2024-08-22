package incerpay.payment.domain.vo;

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
    private PaymentStatus status;
    private Money amount;


}
