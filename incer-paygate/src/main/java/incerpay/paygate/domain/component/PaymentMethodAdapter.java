package incerpay.paygate.domain.component;

import incerpay.paygate.domain.enumeration.PaymentType;
import incerpay.paygate.presentation.dto.out.*;


public interface PaymentMethodAdapter {

    CardsView findMethodsFor(PaymentType pv, String sellerKey);

}
