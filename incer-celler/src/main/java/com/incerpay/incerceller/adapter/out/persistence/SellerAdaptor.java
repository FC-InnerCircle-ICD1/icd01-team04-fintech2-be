package com.incerpay.incerceller.adapter.out.persistence;

import com.incerpay.incerceller.adapter.out.persistence.jpa.entity.SellerEntity;
import com.incerpay.incerceller.adapter.out.persistence.jpa.repository.SellerRepository;
import com.incerpay.incerceller.application.port.out.SelectSellerPort;
import com.incerpay.incerceller.application.port.out.UpdateSellerPort;
import com.incerpay.incerceller.domain.Seller;
import com.incerpay.incerceller.mapper.SellerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class SellerAdaptor implements SelectSellerPort, UpdateSellerPort {

	private final SellerRepository sellerRepository;
	private final SellerMapper sellerMapper;

	@Override
	public Seller selectSeller(Long sellerId) {
		return sellerMapper.toDomain(sellerRepository.findById(sellerId)
				.orElseThrow(() -> new IllegalArgumentException("상점을 찾을 수 없습니다.")));
	}

	@Override
	public List<Seller> selectSellers(Long userId) {
		return sellerRepository.findAllByUserId(userId)
				.stream().map(sellerMapper::toDomain).toList();
	}

	@Override
	public void updateSeller(Long sellerId) {
		SellerEntity seller = sellerRepository.findById(sellerId)
				.orElseThrow(() -> new IllegalArgumentException("상점을 찾을 수 없습니다."));

	}

}
