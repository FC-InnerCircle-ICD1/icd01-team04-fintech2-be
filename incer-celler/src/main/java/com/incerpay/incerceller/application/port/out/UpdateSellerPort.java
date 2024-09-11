package com.incerpay.incerceller.application.port.out;

import com.incerpay.incerceller.domain.Seller;

import java.util.List;

public interface UpdateSellerPort {
	// todo 특정 필드들을 update 한다면, 1개의 update 포트에서만 하나?
	void updateSeller(Long sellerId);

}
