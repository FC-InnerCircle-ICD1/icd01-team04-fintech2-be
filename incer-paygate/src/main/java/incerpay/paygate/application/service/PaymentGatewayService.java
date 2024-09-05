package incerpay.paygate.application.service;

import incerpay.paygate.domain.component.CardApiAdapter;
import incerpay.paygate.domain.component.CardPersistenceAdapter;
import incerpay.paygate.domain.component.CardGatewayValidator;
import incerpay.paygate.domain.component.CardGatewayViewer;
import incerpay.paygate.domain.vo.PaymentIdentification;
import incerpay.paygate.domain.vo.TransactionIdentification;
import incerpay.paygate.presentation.dto.in.*;
import incerpay.paygate.presentation.dto.out.ApiAdapterView;
import incerpay.paygate.presentation.dto.out.ApiStatusView;
import incerpay.paygate.presentation.dto.out.PaymentStateView;
import incerpay.paygate.presentation.dto.out.PersistenceView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PaymentGatewayService {

    private final CardGatewayValidator validator;
    private final CardGatewayViewer viewer;
    private final CardApiAdapter paymentApiAdapter;
    private final CardPersistenceAdapter persistenceAdapter;

    @Transactional
    public PaymentStateView request(PaymentRequestCommand command) {
        validator.validate(command);
        ApiAdapterView apiView = paymentApiAdapter.request(command);
        PersistenceView pv = persistenceAdapter.request(command);
        return viewer.read(pv);
    }

    @Transactional
    public PaymentStateView confirm(PaymentApproveCommand command) {
        validator.validate(command);
        ApiAdapterView apiView = paymentApiAdapter.confirm(command);
        PersistenceView pv = persistenceAdapter.approve(command);
        return viewer.read(pv);
    }

    @Transactional
    public PaymentStateView cancel(PaymentCancelCommand command) {
        validator.validate(command);
        ApiAdapterView apiView = paymentApiAdapter.cancel(command);
        PersistenceView pv = persistenceAdapter.cancel(command);
        return viewer.read(pv);
    }

    @Transactional
    public PaymentStateView reject(PaymentRejectCommand command) {
        validator.validate(command);
        ApiAdapterView apiView = paymentApiAdapter.reject(command);
        PersistenceView pv = persistenceAdapter.reject(command);
        return viewer.read(pv);
    }

    @Transactional
    public PaymentStateView readStatusByPaymentId(String paymentId, String publicKey) {
        PaymentIdentification id = new PaymentIdentification(paymentId, publicKey);
        validator.validate(id);
        ApiStatusView apiView = paymentApiAdapter.readStatus(id);
        return viewer.read(apiView);
    }

    @Transactional
    public PaymentStateView readStatusByTransactionId(String transactionId, String publicKey) {
        TransactionIdentification id = new TransactionIdentification(transactionId, publicKey);
        validator.validate(id);
        ApiStatusView apiView = paymentApiAdapter.readStatus(id);
        return viewer.read(apiView);
    }

}
