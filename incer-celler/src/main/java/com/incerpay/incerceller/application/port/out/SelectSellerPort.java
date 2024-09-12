package com.incerpay.incerceller.application.port.out;

import com.incerpay.incerceller.domain.Seller;

import java.util.List;

public interface SelectSellerPort {
	List<Seller> selectSellers(Long userId);
	Seller selectSeller(Long sellerId);
}
