package incerpay.paygate.common.exception;

public class IncerPayRateLimitException extends RuntimeException {

    public IncerPayRateLimitException() {
        super("시도 횟수 초과");
    }

    public IncerPayRateLimitException(String message) {
        super(message);
    }

    public IncerPayRateLimitException(String message, Throwable cause) {
        super(message, cause);
    }
}

