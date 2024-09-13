package com.incerpay.incerceller.application.service;

import com.incerpay.incerceller.application.port.in.GetSellerUseCase;
import com.incerpay.incerceller.application.port.out.SelectSellerPort;
import com.incerpay.incerceller.application.port.out.UpdateSellerPort;
import com.incerpay.incerceller.domain.Seller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SellerService implements GetSellerUseCase {

	private final SelectSellerPort selectSellerPort;
	private final UpdateSellerPort updateSellerPort;

	@Override
	public List<Seller> getSellers(Long userId) {
		//todo :validator 추가
		return selectSellerPort.selectSellers(userId);
	}

	@Override
	public Seller getSeller(Long sellerId) {
		return selectSellerPort.selectSeller(sellerId);
	}

}
