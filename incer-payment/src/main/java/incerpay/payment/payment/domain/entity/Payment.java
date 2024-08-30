package incerpay.payment.payment.domain.entity;

import incerpay.payment.payment.domain.vo.PaymentProperty;
import incerpay.payment.payment.domain.vo.PaymentState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cascade;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

@Accessors(fluent = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Payment {
    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;
    private PaymentState state;
    private String sellerId;
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
    private Instant rejectedAt;
//    private Instant finishedAt;

    public void approve(Clock clock) {
        if(isApproved()) return;
        this.state = PaymentState.APPROVED;
        this.paymentApproved = PaymentApproved.builder()
                .paymentProperty(this.paymentProperty)
                .approvedAt(clock.instant())
                .build();
    }

    public void cancel(Clock clock) {
        this.state = PaymentState.CANCELED;
        this.paymentCanceled = PaymentCanceled.builder()
                .paymentProperty(this.paymentProperty)
                .canceledAt(clock.instant())
                .build();
    }

    public void reject(Clock clock) {
        this.state = PaymentState.REJECTED;
        this.rejectedAt = clock.instant();
    }

    // 필요하지 않을까..?
//    public void finish(Clock clock) {
//        this.state = PaymentState.FINISHED;
//        this.finishedAt = clock.instant();
//    }

    public boolean isApproved() {
        return this.state == PaymentState.APPROVED
                || this.paymentApproved != null;
    }

//    public boolean isFinished() {
//        return this.state == PaymentState.FINISHED;
//    }

    public boolean isPending() {
        return this.state == PaymentState.PENDING;
    }
}
