package incerpay.paygate.domain.component;

import incerpay.paygate.presentation.dto.out.ApiStatusView;
import incerpay.paygate.presentation.dto.out.PaymentStateListView;
import incerpay.paygate.presentation.dto.out.PaymentStateView;
import incerpay.paygate.presentation.dto.out.PersistenceView;

import java.util.List;


public interface PaymentGatewayViewer {

    PaymentStateView read(PersistenceView pv);
    PaymentStateListView read(List<PersistenceView> pv);
    PaymentStateView read(ApiStatusView apiStatusView);

}
