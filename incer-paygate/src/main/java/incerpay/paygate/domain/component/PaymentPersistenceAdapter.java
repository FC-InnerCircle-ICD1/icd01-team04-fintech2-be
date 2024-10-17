package incerpay.paygate.domain.component;

import incerpay.paygate.infrastructure.external.dto.IncerPaymentApiDataView;
import incerpay.paygate.infrastructure.internal.IncerPaymentApi;
import incerpay.paygate.infrastructure.internal.dto.IncerPaymentApiView;
import incerpay.paygate.presentation.dto.in.*;
import incerpay.paygate.presentation.dto.out.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentPersistenceAdapter {

    private final IncerPaymentApi incerPaymentApi;
    private final IncerPaymentApiMapper incerPaymentApiMapper;

    public PaymentPersistenceAdapter(IncerPaymentApi incerPaymentApi,
                                     IncerPaymentApiMapper incerPaymentApiMapper) {
        this.incerPaymentApi = incerPaymentApi;
        this.incerPaymentApiMapper = incerPaymentApiMapper;
    }


    public PersistenceView request(PaymentRequestCommand paymentRequestCommand) {
        IncerPaymentApiRequestCommand command = incerPaymentApiMapper.toApiRequestCommand(paymentRequestCommand);
        IncerPaymentApiView view = incerPaymentApi.request(command);
        return paymentViewToPersistenceView(view);
    }


    public PersistenceView cancel(PaymentCancelCommand paymentCancelCommand) {
        IncerPaymentApiCancelCommand command = incerPaymentApiMapper.toApiCancelCommand(paymentCancelCommand);
        IncerPaymentApiView view = incerPaymentApi.cancel(command);
        return paymentViewToPersistenceView(view);
    }


    public PersistenceView reject(PaymentRejectCommand paymentRejectCommand) {
        IncerPaymentApiRejectCommand command = incerPaymentApiMapper.toApiRejectCommand(paymentRejectCommand);
        IncerPaymentApiView view = incerPaymentApi.reject(command);
        return paymentViewToPersistenceView(view);
    }


    public PersistenceView approve(PaymentApproveCommand paymentApproveCommand) {
        IncerPaymentApiApproveCommand command = incerPaymentApiMapper.toApiApproveCommand(paymentApproveCommand);
        IncerPaymentApiView view = incerPaymentApi.approve(command);
        return paymentViewToPersistenceView(view);
    }

    private PersistenceView paymentViewToPersistenceView(IncerPaymentApiView view) {

        return new PersistenceView(
                view.data().paymentId(),
                UUID.randomUUID(),
                view.data().sellerId(),
                view.data().state(),
                view.data().price()
        );

    }
}
