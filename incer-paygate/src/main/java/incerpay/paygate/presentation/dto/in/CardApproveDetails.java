package incerpay.paygate.presentation.dto.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CardApproveDetails implements PaymentApproveDetails {

    int installmentPeriod;
    Long amount;

    public CardApproveDetails() {}

    @JsonCreator
    public CardApproveDetails(
            @JsonProperty("installmentPeriod") int installmentPeriod,
            @JsonProperty("amount") Long amount){
        this.installmentPeriod = installmentPeriod;
        this.amount = amount;
    }

    public int getInstallmentPeriod() {
        return installmentPeriod;
    }

    public Long getAmount() {
        return amount;
    }
}
