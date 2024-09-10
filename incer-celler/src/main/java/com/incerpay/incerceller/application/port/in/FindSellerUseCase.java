package com.incerpay.incerceller.application.port.in;

import com.incerpay.incerceller.domain.Seller;

import java.util.List;

public interface FindSellerUseCase {
	List<Seller> findSellers(Long adminId);
	Seller findSeller(Long sellerId);
}
