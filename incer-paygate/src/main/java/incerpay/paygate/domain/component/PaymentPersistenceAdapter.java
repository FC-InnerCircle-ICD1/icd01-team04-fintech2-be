package incerpay.paygate.domain.component;

import incerpay.paygate.infrastructure.internal.IncerPaymentApi;
import incerpay.paygate.infrastructure.internal.dto.IncerPaymentApiView;
import incerpay.paygate.presentation.dto.in.*;
import incerpay.paygate.presentation.dto.out.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
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
        log.info("incerPaymentApi.request: " + view.toString());
        return paymentViewToPersistenceView(view);
    }


    public PersistenceView cancel(PaymentCancelCommand paymentCancelCommand) {
        IncerPaymentApiCancelCommand command = incerPaymentApiMapper.toApiCancelCommand(paymentCancelCommand);
        IncerPaymentApiView view = incerPaymentApi.cancel(command);
        log.info("incerPaymentApi.cancel: " + view.toString());
        return paymentViewToPersistenceView(view);
    }


    public PersistenceView reject(PaymentRejectCommand paymentRejectCommand) {
        IncerPaymentApiRejectCommand command = incerPaymentApiMapper.toApiRejectCommand(paymentRejectCommand);
        IncerPaymentApiView view = incerPaymentApi.reject(command);
        log.info("incerPaymentApi.reject: " + view.toString());
        return paymentViewToPersistenceView(view);
    }


    public PersistenceView approve(PaymentApproveCommand paymentApproveCommand) {
        IncerPaymentApiApproveCommand command = incerPaymentApiMapper.toApiApproveCommand(paymentApproveCommand);
        IncerPaymentApiView view = incerPaymentApi.approve(command);
        log.info("incerPaymentApi.approve: " + view.toString());
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
