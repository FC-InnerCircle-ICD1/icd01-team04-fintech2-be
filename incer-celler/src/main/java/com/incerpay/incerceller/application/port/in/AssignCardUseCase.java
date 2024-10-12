package com.incerpay.incerceller.application.port.in;

import com.incerpay.incerceller.domain.CardCompany;
import com.incerpay.incerceller.domain.PaymentMethod;

import java.util.List;

public interface AssignCardUseCase {
    void assignCard(Long sellerId, List<PaymentMethod> paymentMethods, List<CardCompany> cardCompanies);

}
