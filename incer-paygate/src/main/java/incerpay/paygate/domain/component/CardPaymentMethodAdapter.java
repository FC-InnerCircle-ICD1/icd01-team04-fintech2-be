package incerpay.paygate.domain.component;

import incerpay.paygate.domain.enumeration.PaymentType;
import incerpay.paygate.infrastructure.internal.IncerPaymentStoreCaller;
import incerpay.paygate.infrastructure.internal.dto.SellerApiView;
import incerpay.paygate.infrastructure.internal.dto.TermsApiView;
import incerpay.paygate.presentation.dto.out.ReadyView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CardPaymentMethodAdapter implements PaymentMethodAdapter{

    private final IncerPaymentStoreCaller caller;

    public CardPaymentMethodAdapter(IncerPaymentStoreCaller caller) {
        this.caller = caller;
    }

    public ReadyView findMethodsFor(PaymentType pv, String sellerKey) {
        SellerApiView sellerView = caller.getSeller(Long.valueOf(sellerKey));
        TermsApiView termView = caller.getTerms();
        ReadyView view = ReadyView.from(sellerView, termView);
        log.info("CardPaymentMethodAdapter.findMethodsFor: " + view.toString());
        return view;
    }

}
