package com.incerpay.incerceller.application.port.out;

import com.incerpay.incerceller.domain.Seller;

import java.util.List;

public interface SelectSellerPort {
	Seller selectSeller(Long sellerId);

}
