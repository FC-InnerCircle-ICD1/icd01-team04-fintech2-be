package incerpay.paygate.presentation.dto.out;

import java.util.List;

public record CardsView(
        List<CardDataView> cardlist
) { }
