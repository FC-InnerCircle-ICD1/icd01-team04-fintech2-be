package com.incerpay.incerceller.valid;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })  // 클래스에 적용
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CardRequestValidator.class)  // Validator 클래스와 연결
public @interface ValidCardRequest {

    String message() default "Invalid card company when payment method is missing";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
