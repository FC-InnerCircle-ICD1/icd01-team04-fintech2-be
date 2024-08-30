package incerpay.payment.card.infrastructure;

import incerpay.payment.card.domain.component.CardReader;
import incerpay.payment.card.domain.component.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CardRepositoryImplement implements CardRepository, CardReader {
    private final CardJpaRepository jpaRepository;
}
