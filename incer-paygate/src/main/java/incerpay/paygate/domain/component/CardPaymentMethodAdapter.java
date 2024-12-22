package incerpay.paygate.domain.component;

import incerpay.paygate.domain.enumeration.PaymentType;
import incerpay.paygate.infrastructure.internal.IncerPaymentStoreCaller;
import incerpay.paygate.infrastructure.internal.dto.SellerApiView;
import incerpay.paygate.infrastructure.internal.dto.TermsApiView;
import incerpay.paygate.presentation.dto.out.CardDataView;
import incerpay.paygate.presentation.dto.out.ReadyView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CardPaymentMethodAdapter implements PaymentMethodAdapter{

    private final IncerPaymentStoreCaller caller;

    public CardPaymentMethodAdapter(IncerPaymentStoreCaller caller) {
        this.caller = caller;
    }

    @Override
    public List<CardDataView> getCardsInfo(PaymentType pv, Long sellerKey) {
        SellerApiView sellerView = caller.getSeller(sellerKey);
        return sellerView.getCardDataViewList();
    }

    public ReadyView getPaymentInfo(Long sellerKey) {
        SellerApiView sellerView = caller.getSeller(sellerKey);
        TermsApiView termView = caller.getTerms();
        ReadyView view = ReadyView.from(sellerView, termView);
        log.info("CardPaymentMethodAdapter.getPaymentInfo: " + view.toString());
        return view;
    }

}
