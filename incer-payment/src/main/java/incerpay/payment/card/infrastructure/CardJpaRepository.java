package incerpay.payment.card.infrastructure;

import incerpay.payment.card.domain.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardJpaRepository extends JpaRepository<Card, Long> {

}
