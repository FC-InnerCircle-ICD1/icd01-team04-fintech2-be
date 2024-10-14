package incerpay.paygate.domain.component;

import incerpay.paygate.domain.vo.PaymentIdentification;
import incerpay.paygate.domain.vo.SellerIdentification;
import incerpay.paygate.domain.vo.TransactionIdentification;
import incerpay.paygate.infrastructure.external.IncerPaymentApi;
import incerpay.paygate.infrastructure.external.dto.*;
import incerpay.paygate.presentation.dto.out.PersistenceView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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

        return paymentViewtoPersistenceView(paymentView);
    }

    public PersistenceView readStatusByPaymentId(PaymentIdentification id) {

        IncerPaymentApiListView paymentView = api.readByPaymentId(id.sellerId(), id.paymentId());
        log.info("readStatusByPaymentId.paymentView: " + paymentView.toString());

        return paymentViewtoPersistenceView(paymentView);
    }

    public PersistenceView readStatusByTransactionId(TransactionIdentification id) {

        IncerPaymentApiListView paymentView = api.readBySellerId(id.transactionId());

        log.info("readStatusByTransactionId.paymentView: " + paymentView.toString());

        return paymentViewtoPersistenceView(paymentView);
    }

    private PersistenceView paymentViewtoPersistenceView(IncerPaymentApiListView view) {

        return new PersistenceView();

//
//        if(view.payments()  instanceof IncerPaymentSuccessData data) {
//            return new PersistenceView(
//                    data.paymentId(),
//                    UUID.randomUUID(),
//                    data.sellerId(),
//                    data.state(),
//                    data.price()
//            );
//        }
//
//        throw new RuntimeException();
    }
}
