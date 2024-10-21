package incerpay.paygate.application.service;

import incerpay.paygate.application.factory.PaymentApiAdapterFactory;
import incerpay.paygate.domain.component.*;
import incerpay.paygate.domain.enumeration.PaymentType;
import incerpay.paygate.domain.vo.PaymentIdentification;
import incerpay.paygate.domain.vo.SellerIdentification;
import incerpay.paygate.presentation.dto.in.*;
import incerpay.paygate.presentation.dto.out.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentGatewayService {

    private final PaymentGatewayValidator validator;
    private final PaymentGatewayViewer viewer;
    private final PaymentPersistenceAdapter persistenceAdapter;
    private final PaymentApiAdapterFactory paymentApiAdapterFactory;
    private final CommonApiAdapter commonApiAdapter;

    @Transactional
    public PaymentStateView request(PaymentRequestCommand command) {
        validator.validate(command);
        ApiAdapterView apiView = getMatchedPaymentApi(command.type()).request(command);
        PersistenceView pv = persistenceAdapter.request(command);
        return viewer.read(pv);
    }

    @Transactional
    public PaymentStateView confirm(PaymentApproveCommand command) {
        validator.validate(command);
        ApiAdapterView apiView = getMatchedPaymentApi(command.type()).confirm(command);
        PersistenceView pv = persistenceAdapter.approve(command);
        return viewer.read(pv);
    }

    @Transactional
    public PaymentStateView cancel(PaymentCancelCommand command) {
        validator.validate(command);
        ApiAdapterView apiView = getMatchedPaymentApi(command.type()).cancel(command);
        PersistenceView pv = persistenceAdapter.cancel(command);
        return viewer.read(pv);
    }

    @Transactional
    public PaymentStateView reject(PaymentRejectCommand command) {
        validator.validate(command);
        ApiAdapterView apiView = getMatchedPaymentApi(command.type()).reject(command);
        PersistenceView pv = persistenceAdapter.reject(command);
        return viewer.read(pv);
    }

    @Transactional
    public PaymentStateListView readStatusBySellerId(String sellerId) {
        SellerIdentification id = new SellerIdentification(sellerId);
        validator.validate(id);
        List<PersistenceView> pv = commonApiAdapter.readStatusBySellerId(id);
        return viewer.read(pv);
    }

    @Transactional
    public PaymentStateView readStatusByPaymentId(String sellerId, String paymentId) {
        PaymentIdentification id = new PaymentIdentification(sellerId, paymentId);
        validator.validate(id);
        PersistenceView pv = commonApiAdapter.readStatusByPaymentId(id);
        return viewer.read(pv);
    }

//    @Transactional
//    public PaymentStateView readStatusByTransactionId(String transactionId) {
//        TransactionIdentification id = new TransactionIdentification(transactionId);
//        validator.validate(id);
//        PersistenceView pv = commonApiAdapter.readStatusByTransactionId(id);
//        return viewer.read(pv);
//    }


    private PaymentApiAdapter getMatchedPaymentApi(PaymentType paymentType) {
        return paymentApiAdapterFactory.getAdapter(paymentType);
    }
}
