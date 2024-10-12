package com.incerpay.incerceller.application.dto;

import com.incerpay.incerceller.domain.CardCompany;
import com.incerpay.incerceller.domain.PaymentMethod;
import com.incerpay.incerceller.valid.ValidCardRequest;

import java.util.List;

@ValidCardRequest
public record CardRegisterRequest(Long sellerId, List<PaymentMethod> paymentMethod, List<CardCompany> cardCompany) {
}
