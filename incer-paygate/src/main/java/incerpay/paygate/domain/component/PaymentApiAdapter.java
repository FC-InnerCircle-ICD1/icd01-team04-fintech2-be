package incerpay.paygate.domain.component;

import incerpay.paygate.domain.vo.PaymentIdentification;
import incerpay.paygate.domain.vo.TransactionIdentification;
import incerpay.paygate.presentation.dto.in.*;
import incerpay.paygate.presentation.dto.out.ApiAdapterView;
import incerpay.paygate.presentation.dto.out.ApiStatusView;

public interface PaymentApiAdapter {
    ApiAdapterView request(PaymentRequestCommand paymentRequestCommand);
    ApiAdapterView cancel(PaymentCancelCommand paymentCancelCommand);
    ApiAdapterView reject(PaymentRejectCommand paymentRejectCommand);
    ApiAdapterView confirm(PaymentApproveCommand paymentApproveCommand);
    ApiStatusView readStatus(PaymentIdentification id);
    ApiStatusView readStatus(TransactionIdentification id);

}
