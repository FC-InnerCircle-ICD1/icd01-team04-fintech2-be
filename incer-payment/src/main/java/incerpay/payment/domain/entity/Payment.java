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
        PaymentState currentState = this.paymentProperty.state();
        this.paymentProperty = PaymentProperty.builder()
                .amount(this.paymentProperty.amount())
                .state(PaymentState.APPROVED)
                .registeredAt(clock.instant())
                .build();
        PaymentLedger approved = PaymentLedger.builder()
                .paymentProperty(this.paymentProperty)
                .payment(this)
                .build();
        this.paymentLedgers.add(approved);
        switch (currentState) {
            case PENDING -> {}
        }
    }

    public void confirm(Clock clock) {
        Instant now = clock.instant();
        PaymentState currentState = this.paymentProperty.state();
        this.paymentProperty = PaymentProperty.builder()
                .amount(this.paymentProperty.amount())
                .state(PaymentState.CONFIRMED)
                .registeredAt(now)
                .build();
        PaymentLedger confirmed = PaymentLedger.builder()
                .paymentProperty(this.paymentProperty)
                .payment(this)
                .build();
        this.paymentLedgers.add(confirmed);
        switch (currentState){
            case APPROVED -> this.paymentWallet.conduct(this.paymentProperty.amount());
        }
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
                .payment(this)
                .build();
        this.paymentLedgers.add(canceled);
        switch (currentState){
            case PENDING, APPROVED -> {}
            case CONFIRMED -> this.paymentWallet.deduct(this.paymentProperty.amount());
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
                .payment(this)
                .build();
        this.paymentLedgers.add(rejected);
        switch (currentState){
            case PENDING, APPROVED -> {}
            case CONFIRMED -> this.paymentWallet.deduct(this.paymentProperty.amount());
        }
        finish(now);
    }

    public void settle(Clock clock) {
        Instant now = clock.instant();
        PaymentState currentState = this.paymentProperty.state();
        this.paymentProperty = PaymentProperty.builder()
                .amount(this.paymentProperty.amount())
                .state(PaymentState.SETTLED)
                .registeredAt(now)
                .build();
        PaymentLedger settled = PaymentLedger.builder()
                .paymentProperty(this.paymentProperty)
                .payment(this)
                .build();
        this.paymentLedgers.add(settled);
        switch (currentState) {
            case CONFIRMED -> this.paymentWallet.deduct(this.paymentProperty.amount());
        }
        finish(now);
    }

    public void finish(Instant instant) {
        if(!this.paymentWallet.isEmpty()){
            throw new PaymentStateException("결제가 완료되지 않았습니다. - paymentId: %s".formatted(this.id));
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
