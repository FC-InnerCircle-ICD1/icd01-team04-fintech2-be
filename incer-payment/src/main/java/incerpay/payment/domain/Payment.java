package incerpay.payment.domain;

import incerpay.payment.domain.vo.PaymentProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Payment {
    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;
    @Embedded
    private PaymentProperty paymentProperty;

    @OneToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    PaymentApproved paymentApproved;

    @OneToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    PaymentCanceled paymentCanceled;

    private Instant createdAt;
    private Instant expiredAt;

    public void cancel(Clock clock) {
        this.paymentCanceled = PaymentCanceled.builder()
                .paymentProperty(this.paymentProperty)
                .canceledAt(clock.instant())
                .build();
    }
}
