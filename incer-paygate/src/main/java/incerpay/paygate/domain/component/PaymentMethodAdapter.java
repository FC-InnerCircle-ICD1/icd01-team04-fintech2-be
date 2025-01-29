package incerpay.paygate.domain.component;

import incerpay.paygate.domain.enumeration.PaymentType;
import incerpay.paygate.presentation.dto.out.*;

import java.util.List;


public interface PaymentMethodAdapter {

    List<CardDataView> getCardsInfo(PaymentType pv, Long sellerKey);

    ReadyView getPaymentInfo(Long sellerKey);

}
