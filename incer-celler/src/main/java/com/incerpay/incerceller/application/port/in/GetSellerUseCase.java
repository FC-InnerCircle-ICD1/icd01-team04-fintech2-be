package com.incerpay.incerceller.application.port.in;

import com.incerpay.incerceller.domain.Seller;

public interface GetSellerUseCase {
	Seller getSeller(Long sellerId);

}
