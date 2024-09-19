package com.incerpay.incerceller.valid;

import com.incerpay.incerceller.application.dto.CardRegisterRequest;
import com.incerpay.incerceller.domain.CardCompany;
import com.incerpay.incerceller.domain.PaymentMethod;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class CardRequestValidator implements ConstraintValidator<ValidCardRequest, CardRegisterRequest> {
    @Override
    public void initialize(ValidCardRequest constraintAnnotation) {
    }

    @Override
    public boolean isValid(CardRegisterRequest request, ConstraintValidatorContext context) {
        // 유효성 검사 로직
        List<PaymentMethod> paymentMethods = request.paymentMethod();
        List<CardCompany> cardCompanies = request.cardCompany();

        if(paymentMethods == null || paymentMethods.isEmpty()) {
            return false;
        }

        // 특정 값이 포함된 경우에만 cardCompany 허용 (예: CREDIT_CARD)
        if (!paymentMethods.contains(PaymentMethod.CREDIT_CARD)) {
            return !cardCompanies.isEmpty();  // cardCompany도 없어야 함
        }

        return true;
    }
}
