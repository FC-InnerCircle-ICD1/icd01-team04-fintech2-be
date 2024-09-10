package incerpay.paygate.domain.component;

import incerpay.paygate.domain.vo.PaymentIdentification;
import incerpay.paygate.domain.vo.TransactionIdentification;
import incerpay.paygate.presentation.dto.out.ApiStatusView;
import org.springframework.stereotype.Component;

@Component
public class CommonApiAdapter {

    public ApiStatusView readStatus(TransactionIdentification command) {
        return null;
    }

    public ApiStatusView readStatus(PaymentIdentification command) {
        return null;
    }
}
