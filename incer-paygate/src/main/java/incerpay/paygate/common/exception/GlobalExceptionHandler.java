package incerpay.paygate.common.exception;

import feign.FeignException;
import incerpay.paygate.common.lib.response.Response;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public Response handleException(Exception ex) {
        log.error("Internal server error", ex);
        return Response.error("Internal server error");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Response handleIllegalArgumentException(IllegalArgumentException ex) {
        String errorMessage = ex.getMessage() != null ? ex.getMessage() : "Bad request";
        log.error(errorMessage, ex);
        return Response.fail(errorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = error instanceof FieldError ? ((FieldError) error).getField() : "unknown";
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.error("Validation errors: {}", errors);
        return Response.fail(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Response handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        });
        log.error("Constraint violations: {}", errors);
        return Response.fail(errors);
    }

    @ExceptionHandler(InvalidApiKeyException.class)
    public Response handleInvalidApiKeyException(InvalidApiKeyException ex) {
        String errorMessage = ex.getMessage() != null ? ex.getMessage() : "Bad request";
        log.error(errorMessage, ex);
        return Response.fail(errorMessage);
    }

    @ExceptionHandler(FeignException.class)
    public Response handleFeignException(FeignException ex) {
        String errorMessage = ex.getMessage() != null ? ex.getMessage() : "Bad request";
        log.error(errorMessage, ex);
        // TODO payment 에러 메시지 그대로 반환?
        return Response.fail("거래 실패");
    }

    @ExceptionHandler(IncerPaymentStoreApiFeignException.class)
    public Response handleIncerPaymentStoreApiFeignException(IncerPaymentStoreApiFeignException ex) {
        String errorMessage = ex.getMessage()  != null ? ex.getMessage() : "Bad request";
        log.error(errorMessage, ex);
        return Response.fail(errorMessage);
    }


}