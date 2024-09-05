package incerpay.paygate.domain.component;

import incerpay.paygate.presentation.dto.out.ApiStatusView;
import incerpay.paygate.presentation.dto.out.PaymentStateView;
import incerpay.paygate.presentation.dto.out.PersistenceView;
import org.springframework.stereotype.Component;


@Component
public class CardGatewayViewer implements PaymentGatewayViewer{

    @Override
    public PaymentStateView read(PersistenceView pv) {
        return null;
    }

    @Override
    public PaymentStateView read(ApiStatusView apiStatusView) {
        return null;
    }
}
