package incerpay.payment.infrastructure;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import incerpay.payment.common.dto.PaymentDetailView;
import incerpay.payment.common.dto.PaymentLedgerView;
import incerpay.payment.common.dto.PaymentListView;
import incerpay.payment.domain.entity.Payment;
import incerpay.payment.domain.component.PaymentViewer;
import incerpay.payment.domain.component.PaymentRepository;
import incerpay.payment.common.dto.PaymentView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static incerpay.payment.domain.entity.QPayment.payment;
import static incerpay.payment.domain.entity.QPaymentLedger.paymentLedger;

@Slf4j
@RequiredArgsConstructor
@Repository
public class PaymentRepositoryImplement implements PaymentRepository, PaymentViewer {

    private final PaymentJpaRepository jpaRepository;
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public void save(Payment payment) {
        // id와 상태 log
        log.info("Payment Id : {} - {}", payment.id() == null ? "new request" : payment.id(), payment.paymentProperty().state());
        jpaRepository.save(payment);
    }

    @Override
    public Optional<Payment> findById(UUID paymentId) {
        return jpaRepository.findById(paymentId);
    }

    @Override
    public PaymentView readPayment(UUID paymentId) {
        return jpaQueryFactory.select(Projections.constructor(PaymentView.class,
                        payment.id,
                        payment.sellerId,
                        payment.paymentProperty.state,
                        payment.paymentProperty.amount
                ))
                .from(payment)
                .where(payment.id.eq(paymentId))
                .fetchOne();
    }

    @Override
    public PaymentListView readBySellerId(String sellerId) {
        List<PaymentView> list = jpaQueryFactory.select(Projections.constructor(PaymentView.class,
                        payment.id,
                        payment.sellerId,
                        payment.paymentProperty.state,
                        payment.paymentProperty.amount
                ))
                .from(payment)
                .where(payment.sellerId.eq(sellerId))
                .fetch();
        return new PaymentListView(list);
    }

    @Override
    public PaymentDetailView readDetailBySellerId(String sellerId, String paymentId) {
        List<PaymentLedgerView> ledgerList = jpaQueryFactory.select(Projections.constructor(PaymentLedgerView.class,
                        paymentLedger.id,
                        paymentLedger.paymentProperty.state,
                        paymentLedger.paymentProperty.amount,
                        paymentLedger.paymentProperty.registeredAt
                ))
                .from(paymentLedger)
                .join(paymentLedger.payment, payment)
                .where(payment.sellerId.eq(sellerId)
                        .and(payment.id.eq(UUID.fromString(paymentId))))
                .fetch();

        PaymentDetailView result = jpaQueryFactory.select(Projections.constructor(PaymentDetailView.class,
                        payment.id,
                        payment.sellerId,
                        payment.paymentProperty.state,
                        payment.paymentProperty.amount,
                        Expressions.constant(ledgerList)
                ))
                .from(payment)
                .where(payment.sellerId.eq(sellerId)
                        .and(payment.id.eq(UUID.fromString(paymentId)))
                )
                .fetchOne();
        return result;
    }
}
