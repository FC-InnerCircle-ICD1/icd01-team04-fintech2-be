package incerpay.paygate.domain.component;

import incerpay.paygate.presentation.dto.in.*;
import incerpay.paygate.presentation.dto.out.ApiAdapterView;

public interface PaymentApiAdapter {
    ApiAdapterView request(PaymentRequestCommand paymentRequestCommand);
    ApiAdapterView cancel(PaymentCancelCommand paymentCancelCommand);
    ApiAdapterView reject(PaymentRejectCommand paymentRejectCommand);
    ApiAdapterView confirm(PaymentApproveCommand paymentApproveCommand);

}
