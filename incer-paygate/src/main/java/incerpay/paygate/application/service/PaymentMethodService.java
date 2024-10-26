package incerpay.paygate.application.service;

import incerpay.paygate.domain.component.*;
import incerpay.paygate.domain.enumeration.PaymentType;
import incerpay.paygate.presentation.dto.out.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PaymentMethodService {

    private final PaymentMethodAdapter viewer;

    @Transactional(readOnly = true)
    public CardsView findMethodsFor(PaymentType paymentType, String sellerKey) {
        return viewer.findMethodsFor(paymentType, sellerKey);
    }

}
