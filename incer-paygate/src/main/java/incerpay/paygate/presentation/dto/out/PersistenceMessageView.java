package incerpay.paygate.presentation.dto.out;

import incerpay.paygate.domain.enumeration.PaymentState;
import org.javamoney.moneta.Money;

import java.util.UUID;

public record PersistenceMessageView(
    String message
) implements PersistenceView {
}
