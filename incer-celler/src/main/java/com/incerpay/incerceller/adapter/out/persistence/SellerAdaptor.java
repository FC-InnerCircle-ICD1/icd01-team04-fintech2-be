package com.incerpay.incerceller.adapter.out.persistence;

import com.incerpay.incerceller.adapter.out.persistence.jpa.entity.SellerEntity;
import com.incerpay.incerceller.adapter.out.persistence.jpa.repository.SellerRepository;
import com.incerpay.incerceller.application.port.out.SaveSellerPort;
import com.incerpay.incerceller.application.port.out.SelectSellerPort;
import com.incerpay.incerceller.application.port.out.UpdateSellerPort;
import com.incerpay.incerceller.domain.ApiKeyInfo;
import com.incerpay.incerceller.domain.Seller;
import com.incerpay.incerceller.mapper.ApikeyInfoMapper;
import com.incerpay.incerceller.mapper.SellerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SellerAdaptor implements SelectSellerPort, UpdateSellerPort, SaveSellerPort {

	private final SellerRepository sellerRepository;
	private final SellerMapper sellerMapper;
	private final ApikeyInfoMapper apikeyInfoMapper;

	@Override
	public Seller selectSeller(Long sellerId) {
		return sellerMapper.toDomain(sellerRepository.findById(sellerId)
				.orElseThrow(() -> new IllegalArgumentException("상점을 찾을 수 없습니다.")));
	}

	@Override
	public void updateSellerApiKey(Long sellerId, ApiKeyInfo apiKey) {
		SellerEntity seller = sellerRepository.findById(sellerId)
				.orElseThrow(() -> new IllegalArgumentException("상점을 찾을 수 없습니다."));
		seller.addApiKeyInfo(apikeyInfoMapper.toEntity(apiKey));
	}

	@Override
	public void saveSeller(Seller seller) {
		sellerRepository.save(sellerMapper.toSaveEntity(seller));
	}

}
