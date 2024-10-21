package com.incerpay.incerceller.application.port.out;

import com.incerpay.incerceller.domain.ApiKeyInfo;
import com.incerpay.incerceller.domain.CardCompany;
import com.incerpay.incerceller.domain.PaymentMethod;

import java.util.List;

public interface UpdateSellerPort {
	// todo 특정 필드들을 update 한다면, 1개의 update 포트에서만 하나?
	void updateSellerApiKey(Long sellerId, ApiKeyInfo apiKey);
	void updateSellerCardList(Long aLong, List<CardCompany> cardCompanies, List<PaymentMethod> paymentMethods);
}
