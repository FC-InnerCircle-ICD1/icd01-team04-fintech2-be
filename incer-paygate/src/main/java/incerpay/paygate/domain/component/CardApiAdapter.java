package incerpay.paygate.domain.component;

import incerpay.paygate.infrastructure.external.CardPaymentApi;
import incerpay.paygate.infrastructure.external.dto.*;
import incerpay.paygate.presentation.dto.in.*;
import incerpay.paygate.presentation.dto.out.ApiAdapterView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class CardApiAdapter implements PaymentApiAdapter {

    private final CardPaymentApi api;
    private final PaymentCardApiMapper mapper;

    public CardApiAdapter(CardPaymentApi api,
                          PaymentCardApiMapper mapper) {
        this.api = api;
        this.mapper = mapper;
    }

    @Override
    public ApiAdapterView request(PaymentRequestCommand paymentRequestCommand) {

        CardApiCertifyCommand command = mapper.toApiCommand(paymentRequestCommand);
        CardApiCertifyView view = api.certify(command);
        log.info("api.certify: " + view.toString());

        return createApiAdapterView(view);
    }

    @Override
    public ApiAdapterView cancel(PaymentCancelCommand paymentCancelCommand) {

        CardApiCancelCommand command = mapper.toApiCommand(paymentCancelCommand);
        CardApiCancelView view = api.cancel(command);
        log.info("api.cancel: " + view.toString());

        return createApiAdapterView(view);
    }

    @Override
    public ApiAdapterView reject(PaymentRejectCommand paymentRejectCommand) {

        CardApiCancelCommand command = mapper.toApiCommand(paymentRejectCommand);
        CardApiCancelView view = api.cancel(command);
        log.info("api.reject: " + view.toString());

        return createApiAdapterView(view);
    }

    @Override
    public ApiAdapterView confirm(PaymentApproveCommand paymentApproveCommand) {
        CardApiApproveCommand command = mapper.toApiCommand(paymentApproveCommand);
        CardApiApproveView view = api.pay(command);
        log.info("api.confirm: " + view.toString());

        return createApiAdapterView(view);
    }

    private ApiAdapterView createApiAdapterView(CardApiCertifyView view) {
        return new ApiAdapterView(
                UUID.randomUUID(),
                UUID.randomUUID(),
                view.sellerId(),
                view.state(),
                view.price()
        );
    }

    private ApiAdapterView createApiAdapterView(CardApiCancelView view) {

        return new ApiAdapterView(
                view.paymentId(),
                UUID.randomUUID(),
                view.sellerId(),
                view.state(),
                0L
        );
    }

    private ApiAdapterView createApiAdapterView(CardApiApproveView view) {
        return new ApiAdapterView(
                view.paymentId(),
                view.transactionId(),
                view.sellerId(),
                view.state(),
                view.price()
        );
    }

}

