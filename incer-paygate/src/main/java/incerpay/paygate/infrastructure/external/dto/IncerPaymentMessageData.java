package incerpay.paygate.infrastructure.external.dto;

public record IncerPaymentMessageData(
        String message
)  implements IncerPaymentApiDataView {

}