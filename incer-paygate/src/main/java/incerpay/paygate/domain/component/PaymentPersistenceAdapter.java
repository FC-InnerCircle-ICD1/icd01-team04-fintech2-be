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
    private final PaymentApprovalProcessor paymentApprovalProcessor;

    public PaymentPersistenceAdapter(IncerPaymentApi incerPaymentApi,
                                     IncerPaymentApiMapper incerPaymentApiMapper,
                                     PaymentApprovalProcessor paymentApprovalProcessor) {
        this.incerPaymentApi = incerPaymentApi;
        this.incerPaymentApiMapper = incerPaymentApiMapper;
        this.paymentApprovalProcessor = paymentApprovalProcessor;
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
        log.info("Starting payment approval process for command: {}", paymentApproveCommand);
        IncerPaymentApiView view = paymentApprovalProcessor.processApproval(paymentApproveCommand);
        log.info("PaymentPersistenceAdapter.processApproval: " + view.toString());
        return paymentViewToPersistenceView(view);
    }


    private PersistenceView paymentViewToPersistenceView(IncerPaymentApiView view) {
        log.info("Converting IncerPaymentApiView to PersistenceView: " + view.toString());
        return new PersistenceView(
                view.data().paymentId(),
                UUID.randomUUID(),
                view.data().sellerId(),
                view.data().state(),
                view.data().amount()
        );

    }

}
