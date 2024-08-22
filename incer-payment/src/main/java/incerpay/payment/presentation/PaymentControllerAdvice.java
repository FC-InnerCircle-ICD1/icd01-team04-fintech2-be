package incerpay.payment.presentation;

import incerpay.payment.domain.exception.PaymentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "incerpay.payment.presentation")
public class PaymentControllerAdvice {

    @ExceptionHandler(PaymentException.class)
    public PaymentResponse handlePaymentException(PaymentException e) {
        log.warn("PaymentException occurred: {}", e.getMessage());
        return PaymentResponse.fail(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public PaymentResponse handleException(Exception e) {
        log.error("Exception occurred: {}", e.getMessage());
        return PaymentResponse.error(e.getMessage());
    }
}
