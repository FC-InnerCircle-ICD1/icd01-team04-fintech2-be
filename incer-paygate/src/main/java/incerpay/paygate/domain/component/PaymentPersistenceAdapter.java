package incerpay.paygate.domain.component;

import incerpay.paygate.domain.status.PaymentRealtimeState;
import incerpay.paygate.domain.status.PaymentRealtimeStateService;
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
    private final PaymentRealtimeStateService paymentService;

    public PaymentPersistenceAdapter(IncerPaymentApi incerPaymentApi,
                                     IncerPaymentApiMapper incerPaymentApiMapper,
                                     PaymentRealtimeStateService paymentService) {
        this.incerPaymentApi = incerPaymentApi;
        this.incerPaymentApiMapper = incerPaymentApiMapper;
        this.paymentService = paymentService;
    }


    public PersistenceView request(PaymentRequestCommand paymentRequestCommand) {
        IncerPaymentApiRequestCommand command = incerPaymentApiMapper.toApiCommand(paymentRequestCommand);
        IncerPaymentApiView view = incerPaymentApi.request(command);
        log.info("incerPaymentApi.request: " + view.toString());
        return paymentViewToPersistenceView(view);
    }


    public PersistenceView cancel(PaymentCancelCommand paymentCancelCommand) {
        IncerPaymentApiCancelCommand command = incerPaymentApiMapper.toApiCommand(paymentCancelCommand);
        IncerPaymentApiView view = incerPaymentApi.cancel(command);
        log.info("incerPaymentApi.cancel: " + view.toString());
        return paymentViewToPersistenceView(view);
    }


    public PersistenceView reject(PaymentRejectCommand paymentRejectCommand) {
        IncerPaymentApiRejectCommand command = incerPaymentApiMapper.toApiCommand(paymentRejectCommand);
        IncerPaymentApiView view = incerPaymentApi.reject(command);
        log.info("incerPaymentApi.reject: " + view.toString());
        return paymentViewToPersistenceView(view);
    }


    public PersistenceView approve(PaymentApproveCommand paymentApproveCommand) {

        PaymentRealtimeState state = getPaymentRealTimeState(paymentApproveCommand);
        IncerPaymentApiApproveCommand command = incerPaymentApiMapper.toApiCommand(paymentApproveCommand);

        IncerPaymentApiView view = incerPaymentApi.approve(command);
        log.info("incerPaymentApi.approve: " + view.toString());

        PaymentRealtimeState savedState = updatePersistenceState(state);
        log.info("updatePersistenceState: " + savedState);

        return paymentViewToPersistenceView(view);
    }

    private PersistenceView paymentViewToPersistenceView(IncerPaymentApiView view) {

        return new PersistenceView(
                view.data().paymentId(),
                UUID.randomUUID(),
                view.data().sellerId(),
                view.data().state(),
                view.data().amount()
        );

    }

    private PaymentRealtimeState getPaymentRealTimeState(PaymentApproveCommand command) {
        return paymentService.getPaymentRealTimeState(command);
    }

    private PaymentRealtimeState updatePersistenceState(PaymentRealtimeState state) {
        state.save();
        return paymentService.savePayment(state);
    }

}
