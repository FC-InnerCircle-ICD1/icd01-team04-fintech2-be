package com.incerpay.incerceller.application.port.in;

import com.incerpay.incerceller.domain.Seller;

import java.util.List;

public interface GetSellerUseCase {
	List<Seller> getSellers(Long userId);
	Seller getSeller(Long sellerId);
}
