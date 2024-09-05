package incerpay.paygate.domain.component;

import incerpay.paygate.presentation.dto.out.ApiStatusView;
import incerpay.paygate.presentation.dto.out.PaymentStateView;
import incerpay.paygate.presentation.dto.out.PersistenceView;


public interface PaymentGatewayViewer {
    PaymentStateView read(PersistenceView pv);
    PaymentStateView read(ApiStatusView apiStatusView);

}
