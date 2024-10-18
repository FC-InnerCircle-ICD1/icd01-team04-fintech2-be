package incerpay.paygate.domain.component;

import incerpay.paygate.presentation.dto.out.ApiStatusView;
import incerpay.paygate.presentation.dto.out.PaymentStateView;
import incerpay.paygate.presentation.dto.out.PersistenceView;
import org.springframework.stereotype.Component;


@Component
public class CardGatewayViewer implements PaymentGatewayViewer{

    @Override
    public PaymentStateView read(PersistenceView pv) {
        return new PaymentStateView(
                pv.paymentId(),
                pv.transactionId(),
                pv.sellerId(),
                pv.state(),
                pv.price()
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
}
