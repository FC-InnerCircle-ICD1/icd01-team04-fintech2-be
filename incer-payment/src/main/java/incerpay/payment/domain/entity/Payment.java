package incerpay.payment.domain.entity;

import incerpay.payment.common.exception.PaymentStateException;
import incerpay.payment.domain.vo.PaymentProperty;
import incerpay.payment.domain.vo.PaymentState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cascade;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
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
    private String sellerId;

    @Embedded
    private PaymentProperty paymentProperty;

    @OneToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    PaymentWallet paymentWallet;

    @OneToMany(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    List<PaymentLedger> paymentLedgers;

    private Instant createdAt;
    private Instant expiredAt;
    private Instant finishedAt;

    public static Payment of(String sellerId, Long amount, Instant createdAt) {
        return Payment.builder()
                .sellerId(sellerId)
                .paymentProperty(PaymentProperty.builder()
                        .amount(amount)
                        .state(PaymentState.PENDING)
                        .registeredAt(createdAt)
                        .build())
                .createdAt(createdAt)
                .expiredAt(createdAt.plus(10, ChronoUnit.MINUTES))
                .paymentLedgers(new ArrayList<>())
                .paymentWallet(PaymentWallet.builder()
                        .balance(0L)
                        .build())
                .build();
    }

    public void approve(Clock clock) {
        this.paymentProperty = PaymentProperty.builder()
                .amount(this.paymentProperty.amount())
                .state(PaymentState.APPROVED)
                .registeredAt(clock.instant())
                .build();
        PaymentLedger approved = PaymentLedger.builder()
                .paymentProperty(this.paymentProperty)
                .build();
        this.paymentLedgers.add(approved);
        this.paymentWallet.conduct(this.paymentProperty.amount());
    }

    public void cancel(Clock clock) {
        Instant now = clock.instant();
        PaymentState currentState = this.paymentProperty.state();
        this.paymentProperty = PaymentProperty.builder()
                .amount(this.paymentProperty.amount())
                .state(PaymentState.CANCELED)
                .registeredAt(now)
                .build();
        PaymentLedger canceled = PaymentLedger.builder()
                .paymentProperty(this.paymentProperty)
                .build();
        this.paymentLedgers.add(canceled);
        switch (currentState){
            case PENDING -> {}
            case APPROVED -> this.paymentWallet.deduct(this.paymentProperty.amount());
            default -> throw new PaymentStateException("결제 상태를 변경할 수 없습니다.");
        }
        finish(now);
    }

    public void reject(Clock clock) {
        Instant now = clock.instant();
        PaymentState currentState = this.paymentProperty.state();
        this.paymentProperty = PaymentProperty.builder()
                .amount(this.paymentProperty.amount())
                .state(PaymentState.REJECTED)
                .registeredAt(now)
                .build();
        PaymentLedger rejected = PaymentLedger.builder()
                .paymentProperty(this.paymentProperty)
                .build();
        this.paymentLedgers.add(rejected);
        switch (currentState){
            case PENDING -> {}
            case APPROVED -> this.paymentWallet.deduct(this.paymentProperty.amount());
            default -> throw new PaymentStateException("결제 상태를 변경할 수 없습니다.");
        }
        finish(now);
    }

    public void settle(Clock clock) {
        Instant now = clock.instant();
        this.paymentProperty = PaymentProperty.builder()
                .amount(this.paymentProperty.amount())
                .state(PaymentState.SETTLED)
                .registeredAt(now)
                .build();
        PaymentLedger reconciled = PaymentLedger.builder()
                .paymentProperty(this.paymentProperty)
                .build();
        this.paymentLedgers.add(reconciled);
        this.paymentWallet.deduct(this.paymentProperty.amount());
        finish(now);
    }

    public void finish(Instant instant) {
        if(!this.paymentWallet.isEmpty()){
            throw new PaymentStateException("결제가 완료되지 않았습니다.");
        }
        this.finishedAt = instant;
    }

    public boolean isFinished() {
        return this.finishedAt != null;
    }

    public boolean isExpired() {
        return this.expiredAt.isBefore(Instant.now());
    }
}
