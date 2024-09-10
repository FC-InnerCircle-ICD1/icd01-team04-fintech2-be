package incerpay.paygate.presentation.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CardPaymentDetails implements PaymentMethodDetails {
    String cardNumber;
    String cvc;
    String expireDate;
    String cardCompany;
    String customerId;

    public CardPaymentDetails() {}

    @JsonCreator
    public CardPaymentDetails(
            @JsonProperty("cardNumber") String cardNumber,
            @JsonProperty("cvc") String cvc,
            @JsonProperty("expireDate") String expireDate,
            @JsonProperty("cardCompany") String cardCompany,
            @JsonProperty("customerId") String customerId) {
        this.cardNumber = cardNumber;
        this.cvc = cvc;
        this.expireDate = expireDate;
        this.cardCompany = cardCompany;
        this.customerId = customerId;
    }
}
