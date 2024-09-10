package incerpay.paygate.application.factory;

import incerpay.paygate.domain.component.CardApiAdapter;
import incerpay.paygate.domain.component.PaymentApiAdapter;
import incerpay.paygate.domain.enumeration.PaymentType;
import org.springframework.stereotype.Component;

@Component
public class PaymentApiAdapterFactory {

    private final CardApiAdapter cardApiAdapter;

    public PaymentApiAdapterFactory(CardApiAdapter cardApiAdapter) {
        this.cardApiAdapter = cardApiAdapter;
    }

    public PaymentApiAdapter getAdapter(PaymentType paymentType) {
        if (paymentType == PaymentType.CARD) {
            return cardApiAdapter;
        }
        throw new IllegalArgumentException("지원하지 않는 결제 타입입니다.");
    }
}
