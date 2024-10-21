package incerpay.paygate.domain.component;

import incerpay.paygate.presentation.dto.out.ApiStatusView;
import incerpay.paygate.presentation.dto.out.PaymentStateListView;
import incerpay.paygate.presentation.dto.out.PaymentStateView;
import incerpay.paygate.presentation.dto.out.PersistenceView;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class CardGatewayViewer implements PaymentGatewayViewer{

    @Override
    public PaymentStateView read(PersistenceView pv) {
        return convertToPaymentStateView(pv);
    }

    @Override
    public PaymentStateListView read(List<PersistenceView> pv) {

        PersistenceView firstPv = pv.getFirst();
        List<PaymentStateView> list = pv.stream()
                .map(this::convertToPaymentStateView)
                .collect(Collectors.toList());

        return new PaymentStateListView(
                firstPv.sellerId(),
                list
        );
    }

    @Override
    public PaymentStateView read(ApiStatusView apiStatusView) {

        return new PaymentStateView(
                apiStatusView.paymentId(),
                apiStatusView.transactionId(),
                apiStatusView.sellerId(),
                apiStatusView.state(),
                apiStatusView.price()
        );
    }

    private PaymentStateView convertToPaymentStateView(PersistenceView pv) {
        return new PaymentStateView(
                pv.paymentId(),
                pv.transactionId(),
                pv.sellerId(),
                pv.state(),
                pv.price()
        );
    }
}
