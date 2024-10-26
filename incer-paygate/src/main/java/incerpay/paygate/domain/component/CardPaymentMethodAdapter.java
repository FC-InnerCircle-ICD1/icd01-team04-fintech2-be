package incerpay.paygate.domain.component;

import incerpay.paygate.domain.enumeration.PaymentType;
import incerpay.paygate.infrastructure.internal.IncerPaymentStoreCaller;
import incerpay.paygate.infrastructure.internal.dto.SellerApiView;
import incerpay.paygate.presentation.dto.out.CardsView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CardPaymentMethodAdapter implements PaymentMethodAdapter{

    private final IncerPaymentStoreCaller caller;

    public CardPaymentMethodAdapter(IncerPaymentStoreCaller caller) {
        this.caller = caller;
    }

    public CardsView findMethodsFor(PaymentType pv, String sellerKey) {
        SellerApiView sellerView = caller.getSeller(Long.valueOf(sellerKey));
        CardsView view = sellerView.toCardsView();
        log.info("CardPaymentMethodAdapter.findMethodsFor: " + view.toString());
        return view;
    }

}
