package incerpay.payment.domain.entity;

import incerpay.payment.domain.vo.PaymentProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Accessors(fluent = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class PaymentLedger {
    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;
    @Embedded
    private PaymentProperty paymentProperty;

    @ManyToOne
    private Payment payment;
}
