package com.incerpay.incerceller.application.service;

import com.incerpay.incerceller.application.port.in.AssignSellerUseCase;
import com.incerpay.incerceller.application.port.in.GetSellerUseCase;
import com.incerpay.incerceller.application.port.out.SaveSellerPort;
import com.incerpay.incerceller.application.port.out.SelectSellerPort;
import com.incerpay.incerceller.domain.Seller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SellerService implements GetSellerUseCase, AssignSellerUseCase {

	private final SelectSellerPort selectSellerPort;
	private final SaveSellerPort saveSellerPort;

	@Override
	public Seller getSeller(Long sellerId) {
		return selectSellerPort.selectSeller(sellerId);
	}

	@Override
	@Transactional
	public void assignSeller(Long customerId, String sellerName) {
		saveSellerPort.saveSeller(Seller.builder()
				.customerId(customerId)
				.sellerName(sellerName).build());
	}

}
