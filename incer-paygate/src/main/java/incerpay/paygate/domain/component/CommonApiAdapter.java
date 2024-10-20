package incerpay.paygate.domain.component;

import incerpay.paygate.domain.vo.PaymentIdentification;
import incerpay.paygate.domain.vo.SellerIdentification;
import incerpay.paygate.domain.vo.TransactionIdentification;
import incerpay.paygate.infrastructure.internal.IncerPaymentApi;
import incerpay.paygate.infrastructure.internal.dto.IncerPaymentApiListView;
import incerpay.paygate.presentation.dto.out.PersistenceView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class CommonApiAdapter {

    private final IncerPaymentApi api;
    private final IncerPaymentApiMapper mapper;

    public CommonApiAdapter(IncerPaymentApi api,
                            IncerPaymentApiMapper mapper) {
        this.api = api;
        this.mapper = mapper;
    }

    public PersistenceView readStatusBySellerId(SellerIdentification id) {

        IncerPaymentApiListView paymentView = api.readBySellerId(id.sellerId());
        log.info("readStatusBySellerId.paymentView: " + paymentView.toString());

        return paymentViewToPersistenceView(paymentView);
    }

    public PersistenceView readStatusByPaymentId(PaymentIdentification id) {

        IncerPaymentApiListView paymentView = api.readByPaymentId(id.sellerId(), id.paymentId());
        log.info("readStatusByPaymentId.paymentView: " + paymentView.toString());

        return paymentViewToPersistenceView(paymentView);
    }

    public PersistenceView readStatusByTransactionId(TransactionIdentification id) {

        IncerPaymentApiListView paymentView = api.readBySellerId(id.transactionId());

        log.info("readStatusByTransactionId.paymentView: " + paymentView.toString());

        return paymentViewToPersistenceView(paymentView);
    }

    private PersistenceView paymentViewToPersistenceView(IncerPaymentApiListView view) {

        if(view.payments().size() == 0) {
            throw new RuntimeException();
        }

        return new PersistenceView(
                view.payments().get(0).paymentId(),
                UUID.randomUUID(),
                view.payments().get(0).sellerId(),
                view.payments().get(0).state(),
                view.payments().get(0).amount()
        );

    }
}
