package incerpay.paygate.presentation.dto.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CardApproveDetails implements PaymentApproveDetails {

    int installmentPeriod;
    Long price;
    String cardIdentityCertifyNumber;

    public CardApproveDetails() {}

    @JsonCreator
    public CardApproveDetails(
            @JsonProperty("installmentPeriod") int installmentPeriod,
            @JsonProperty("price") Long price,
            @JsonProperty("certifyNumber") String certifyNumber){
        this.installmentPeriod = installmentPeriod;
        this.price = price;
        this.cardIdentityCertifyNumber = certifyNumber;
    }

    public int getInstallmentPeriod() {
        return installmentPeriod;
    }

    public Long getPrice() {
        return price;
    }

    public String getCardIdentityCertifyNumber() {
        return cardIdentityCertifyNumber;
    }
}
