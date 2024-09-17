package incerpay.paygate.domain.component;

import incerpay.paygate.domain.vo.PaymentState;
import incerpay.paygate.infrastructure.external.CardPaymentApi;
import incerpay.paygate.infrastructure.external.IncerPaymentApi;
import incerpay.paygate.infrastructure.external.dto.CardApiApproveView;
import incerpay.paygate.infrastructure.external.dto.CardApiCancelView;
import incerpay.paygate.infrastructure.external.dto.CardApiCertifyView;
import incerpay.paygate.infrastructure.external.dto.IncerPaymentApiView;
import incerpay.paygate.presentation.dto.in.*;
import incerpay.paygate.presentation.dto.out.ApiAdapterView;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CardApiAdapter implements PaymentApiAdapter {

    private final CardPaymentApi api;
    private final IncerPaymentApi incerPaymentApi;
    private final PaymentCardApiMapper mapper;
    private final IncerPaymentApiMapper incerPaymentApiMapper;

    public CardApiAdapter(CardPaymentApi api,
                          PaymentCardApiMapper mapper,
                          IncerPaymentApi incerPaymentApi,
                          IncerPaymentApiMapper incerPaymentApiMapper) {
        this.api = api;
        this.mapper = mapper;
        this.incerPaymentApi = incerPaymentApi;
        this.incerPaymentApiMapper = incerPaymentApiMapper;
    }

    @Override
    public ApiAdapterView request(PaymentRequestCommand paymentRequestCommand) {
        CardApiCertifyCommand command = mapper.toApiCertifyCommand(paymentRequestCommand);
        CardApiCertifyView view = api.certify(command);

        IncerPaymentApiRequestCommand paymentCommand = incerPaymentApiMapper.toApiRequestCommand(paymentRequestCommand);
        IncerPaymentApiView paymentView = incerPaymentApi.request(paymentCommand);
        return createApiAdapterView(paymentView);
    }

    @Override
    public ApiAdapterView cancel(PaymentCancelCommand paymentCancelCommand) {
        CardApiCancelCommand command = mapper.toApiCancelCommand(paymentCancelCommand);
        CardApiCancelView view = api.cancel(command);

        IncerPaymentApiCancelCommand paymentCommand = incerPaymentApiMapper.toApiCancelCommand(paymentCancelCommand);
        IncerPaymentApiView paymentView = incerPaymentApi.cancel(paymentCommand);
        return createApiAdapterView(paymentView);
    }

    @Override
    public ApiAdapterView reject(PaymentRejectCommand paymentRejectCommand) {
        CardApiCancelCommand command = mapper.toApiCancelCommand(paymentRejectCommand);
        CardApiCancelView view = api.cancel(command);

        IncerPaymentApiRejectCommand paymentCommand = incerPaymentApiMapper.toApiRejectCommand(paymentRejectCommand);
        IncerPaymentApiView paymentView = incerPaymentApi.reject(paymentCommand);
        return createApiAdapterView(paymentView);
    }

    @Override
    public ApiAdapterView confirm(PaymentApproveCommand paymentApproveCommand) {
        CardApiApproveCommand command = mapper.toApiApproveCommand(paymentApproveCommand);
        CardApiApproveView view = api.pay(command);

        IncerPaymentApiApproveCommand paymentCommand = incerPaymentApiMapper.toApiApproveCommand(paymentApproveCommand);
        IncerPaymentApiView paymentView = incerPaymentApi.approve(paymentCommand);
        return createApiAdapterView(paymentView);
    }

    private ApiAdapterView createApiAdapterView(IncerPaymentApiView paymentView) {
        return new ApiAdapterView(
            paymentView.paymentId(),
            UUID.randomUUID(),
            paymentView.sellerId(),
            paymentView.state(),
            paymentView.amount()
        );
    }
}
