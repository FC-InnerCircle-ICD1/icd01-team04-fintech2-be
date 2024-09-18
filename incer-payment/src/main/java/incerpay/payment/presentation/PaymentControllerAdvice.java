package incerpay.payment.presentation;

import incerpay.payment.common.lib.jpa.PersistentNotFoundException;
import incerpay.payment.common.lib.lock.LockManagerException;
import incerpay.payment.common.lib.request.RequestParameterException;
import incerpay.payment.common.lib.response.Response;
import incerpay.payment.common.exception.PaymentStateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "incerpay.payment.presentation")
public class PaymentControllerAdvice {

    @ExceptionHandler(LockManagerException.class)
    public Response handleLockManagerException(LockManagerException e) {
        log.warn("LockManagerException occurred: {}", e.getMessage());
        return Response.fail(e.getMessage());
    }

    @ExceptionHandler(PersistentNotFoundException.class)
    public Response handlePersistentNotFoundException(PersistentNotFoundException e) {
        log.warn("PersistentNotFoundException occurred: {}", e.getMessage());
        return Response.fail(e.getMessage());
    }

    @ExceptionHandler(RequestParameterException.class)
    public Response handleRequestParameterException(RequestParameterException e) {
        log.warn("RequestParameterException occurred: {}", e.getMessage());
        return Response.fail(e.getMessage());
    }

    @ExceptionHandler(PaymentStateException.class)
    public Response handlePaymentException(PaymentStateException e) {
        log.warn("PaymentException occurred: {}", e.getMessage());
        return Response.fail(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e) {
        log.error("Exception occurred: {}", e.getMessage());
        return Response.error(e.getMessage());
    }
}
