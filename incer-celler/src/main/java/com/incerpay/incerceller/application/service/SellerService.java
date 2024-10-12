package com.incerpay.incerceller.application.service;

import com.incerpay.incerceller.application.port.in.AssignCardUseCase;
import com.incerpay.incerceller.application.port.in.AssignSellerUseCase;
import com.incerpay.incerceller.application.port.in.GetSellerUseCase;
import com.incerpay.incerceller.application.port.out.SaveSellerPort;
import com.incerpay.incerceller.application.port.out.SelectSellerPort;
import com.incerpay.incerceller.domain.CardCompany;
import com.incerpay.incerceller.domain.PaymentMethod;
import com.incerpay.incerceller.domain.Seller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SellerService implements GetSellerUseCase, AssignSellerUseCase, AssignCardUseCase {

	private final SelectSellerPort selectSellerPort;
	private final SaveSellerPort saveSellerPort;

	@Override
	public Seller getSeller(Long sellerId) {
		return selectSellerPort.selectSeller(sellerId);
	}

	@Override
	@Transactional
	public void assignSeller(Long sellerId, String sellerName) {
		saveSellerPort.saveSeller(Seller.builder()
				.sellerId(sellerId)
				.sellerName(sellerName).build());
	}

	@Override
	public void assignCard(Long sellerId, List<PaymentMethod> paymentMethods, List<CardCompany> cardCompanies) {
		saveSellerPort.saveSeller(Seller.builder()
				.sellerId(sellerId)
				.paymentMethods(paymentMethods)
				.cardCompanies(cardCompanies).build());
	}

}
