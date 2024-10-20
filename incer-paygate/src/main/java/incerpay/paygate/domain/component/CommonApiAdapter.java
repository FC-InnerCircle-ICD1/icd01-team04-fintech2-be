package incerpay.paygate.domain.component;

import incerpay.paygate.domain.vo.PaymentIdentification;
import incerpay.paygate.domain.vo.SellerIdentification;
import incerpay.paygate.infrastructure.external.dto.IncerPaymentApiDataView;
import incerpay.paygate.infrastructure.internal.IncerPaymentApi;
import incerpay.paygate.infrastructure.internal.dto.IncerPaymentApiListView;
import incerpay.paygate.infrastructure.internal.dto.IncerPaymentApiView;
import incerpay.paygate.presentation.dto.out.PersistenceView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public  List<PersistenceView> readStatusBySellerId(SellerIdentification id) {

        IncerPaymentApiListView paymentView = api.readBySellerId(id.sellerId());
        log.info("readStatusBySellerId.paymentView: " + paymentView.toString());

        return paymentViewToPersistenceListView(paymentView);
    }

    public PersistenceView readStatusByPaymentId(PaymentIdentification id) {

        IncerPaymentApiView paymentView = api.readByPaymentId(id.sellerId(), id.paymentId());
        log.info("readStatusByPaymentId.paymentView: " + paymentView.toString());

        return paymentViewToPersistenceView(paymentView);
    }

//    public PersistenceView readStatusByTransactionId(TransactionIdentification id) {
//
//        IncerPaymentApiView paymentView = api.readByTransactionId(id.transactionId());
//        log.info("readStatusByTransactionId.paymentView: " + paymentView.toString());
//
//        return paymentViewToPersistenceView(paymentView);
//    }

    private List<PersistenceView> paymentViewToPersistenceListView(IncerPaymentApiListView view) {

        List<IncerPaymentApiDataView> paymentDataList = view.data().payments();

        if(paymentDataList.size() == 0) {
            throw new IllegalArgumentException("해당하는 상점의 결제가 없습니다.");
        }

        return paymentDataList.stream()
                .map(this::paymentDataViewToPersistenceView)
                .collect(Collectors.toList());

    }


    private PersistenceView paymentDataViewToPersistenceView(IncerPaymentApiDataView dataView) {
        return new PersistenceView(
                dataView.paymentId(),
                UUID.randomUUID(),
                dataView.sellerId(),
                dataView.state(),
                dataView.amount()
        );
    }


    private PersistenceView paymentViewToPersistenceView(IncerPaymentApiView view) {

        return new PersistenceView(
                view.data().paymentId(),
                UUID.randomUUID(),
                view.data().sellerId(),
                view.data().state(),
                view.data().amount()
        );

    }
}
