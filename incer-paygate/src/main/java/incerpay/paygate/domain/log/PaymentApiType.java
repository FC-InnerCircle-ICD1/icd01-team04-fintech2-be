package incerpay.paygate.domain.log;

import lombok.Getter;

@Getter
public enum PaymentApiType {
    REQUEST, RESPONSE, ERROR
}
