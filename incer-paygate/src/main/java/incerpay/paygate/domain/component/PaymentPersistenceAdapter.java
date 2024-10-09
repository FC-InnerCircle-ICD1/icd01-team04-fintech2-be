package incerpay.paygate.domain.component;

import incerpay.paygate.infrastructure.internal.IncerPaymentApi;
import incerpay.paygate.infrastructure.internal.dto.IncerPaymentApiView;
import incerpay.paygate.presentation.dto.in.*;
import incerpay.paygate.presentation.dto.out.PersistenceView;
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

        return new PersistenceView(
                UUID.randomUUID(),
                UUID.randomUUID(),
                command.sellerId(),
                view.state(),
                0L
        );
    }


    public PersistenceView cancel(PaymentCancelCommand paymentCancelCommand) {
        IncerPaymentApiCancelCommand command = incerPaymentApiMapper.toApiCancelCommand(paymentCancelCommand);
        IncerPaymentApiView view = incerPaymentApi.cancel(command);
        return new PersistenceView(
                view.paymentId(),
                paymentCancelCommand.transactionId(),
                view.sellerId(),
                view.state(),
                view.amount()
        );
    }


    public PersistenceView reject(PaymentRejectCommand paymentRejectCommand) {
        IncerPaymentApiRejectCommand command = incerPaymentApiMapper.toApiRejectCommand(paymentRejectCommand);
        IncerPaymentApiView view = incerPaymentApi.reject(command);
        return new PersistenceView(
                view.paymentId(),
                paymentRejectCommand.transactionId(),
                view.sellerId(),
                view.state(),
                view.amount()
        );
    }


    public PersistenceView approve(PaymentApproveCommand paymentApproveCommand) {
        IncerPaymentApiApproveCommand command = incerPaymentApiMapper.toApiApproveCommand(paymentApproveCommand);
        IncerPaymentApiView view = incerPaymentApi.approve(command);
        return new PersistenceView(
                view.paymentId(),
                paymentApproveCommand.transactionId(),
                paymentApproveCommand.customerId(),
                view.state(),
                view.amount()
        );
    }

}
