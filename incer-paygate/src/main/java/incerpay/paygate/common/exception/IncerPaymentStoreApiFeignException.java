package incerpay.paygate.common.exception;

import feign.FeignException;

public class IncerPaymentStoreApiFeignException extends FeignException {

    public IncerPaymentStoreApiFeignException(String message, int status) {
        super(status, message);
    }

    public IncerPaymentStoreApiFeignException(String message, Throwable cause, int status) {
        super(status, message, cause);
    }

}

