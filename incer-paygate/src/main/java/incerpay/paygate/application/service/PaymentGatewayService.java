package incerpay.paygate.application.service;

import incerpay.paygate.application.factory.PaymentApiAdapterFactory;
import incerpay.paygate.domain.component.*;
import incerpay.paygate.domain.vo.PaymentIdentification;
import incerpay.paygate.domain.vo.TransactionIdentification;
import incerpay.paygate.presentation.dto.in.*;
import incerpay.paygate.presentation.dto.out.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        PaymentApiAdapter adapter = paymentApiAdapterFactory.getAdapter(command.type());
        ApiAdapterView apiView = adapter.request(command);
        PersistenceView pv = persistenceAdapter.request(command);
        return viewer.read(pv);
    }

    @Transactional
    public PaymentStateView confirm(PaymentApproveCommand command) {
        validator.validate(command);
        PaymentApiAdapter adapter = paymentApiAdapterFactory.getAdapter(command.type());
        ApiAdapterView apiView = adapter.confirm(command);
        PersistenceView pv = persistenceAdapter.approve(command);
        return viewer.read(pv);
    }

    @Transactional
    public PaymentStateView cancel(PaymentCancelCommand command) {
        validator.validate(command);
        PaymentApiAdapter adapter = paymentApiAdapterFactory.getAdapter(command.type());
        ApiAdapterView apiView = adapter.cancel(command);
        PersistenceView pv = persistenceAdapter.cancel(command);
        return viewer.read(pv);
    }

    @Transactional
    public PaymentStateView reject(PaymentRejectCommand command) {
        validator.validate(command);
        PaymentApiAdapter adapter = paymentApiAdapterFactory.getAdapter(command.type());
        ApiAdapterView apiView = adapter.reject(command);
        PersistenceView pv = persistenceAdapter.reject(command);
        return viewer.read(pv);
    }

    @Transactional
    public PaymentStateView readStatusByPaymentId(String paymentId) {
        PaymentIdentification id = new PaymentIdentification(paymentId);
        validator.validate(id);
        ApiStatusView apiView = commonApiAdapter.readStatus(id);
        return viewer.read(apiView);
    }

    @Transactional
    public PaymentStateView readStatusByTransactionId(String transactionId) {
        TransactionIdentification id = new TransactionIdentification(transactionId);
        validator.validate(id);
        ApiStatusView apiView = commonApiAdapter.readStatus(id);
        return viewer.read(apiView);
    }

}
