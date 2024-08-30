package incerpay.payment.payment.infrastructure;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import incerpay.payment.payment.domain.dto.PaymentListView;
import incerpay.payment.payment.domain.entity.Payment;
import incerpay.payment.payment.domain.component.PaymentViewer;
import incerpay.payment.payment.domain.component.PaymentRepository;
import incerpay.payment.payment.domain.dto.PaymentStateView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static incerpay.payment.payment.domain.entity.QPayment.payment;

@RequiredArgsConstructor
@Repository
public class PaymentRepositoryImplement implements PaymentRepository, PaymentViewer {

    private final PaymentJpaRepository jpaRepository;
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public void save(Payment payment) {
        jpaRepository.save(payment);
    }

    @Override
    public Optional<Payment> findById(UUID paymentId) {
        return jpaRepository.findById(paymentId);
    }

    @Override
    public PaymentStateView readPayment(UUID paymentId) {
        return jpaQueryFactory.select(Projections.constructor(PaymentStateView.class,
                        payment.id,
                        payment.sellerId,
                        payment.state,
                        payment.paymentProperty.amount
                ))
                .from(payment)
                .where(payment.id.eq(paymentId))
                .fetchOne();
    }

    @Override
    public PaymentListView readBySellerId(String sellerId) {
        List<PaymentStateView> list = jpaQueryFactory.select(Projections.constructor(PaymentStateView.class,
                        payment.id,
                        payment.sellerId,
                        payment.state,
                        payment.paymentProperty.amount
                ))
                .from(payment)
                .where(payment.sellerId.eq(sellerId))
                .fetch();
        return new PaymentListView(list);
    }
}
