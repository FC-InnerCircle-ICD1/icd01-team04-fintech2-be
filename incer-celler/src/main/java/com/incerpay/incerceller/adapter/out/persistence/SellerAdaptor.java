package com.incerpay.incerceller.adapter.out.persistence;

import com.incerpay.incerceller.adapter.out.persistence.jpa.entity.SellerEntity;
import com.incerpay.incerceller.adapter.out.persistence.jpa.repository.SellerRepository;
import com.incerpay.incerceller.application.port.out.SaveSellerPort;
import com.incerpay.incerceller.application.port.out.SelectSellerPort;
import com.incerpay.incerceller.application.port.out.UpdateSellerPort;
import com.incerpay.incerceller.domain.ApiKeyInfo;
import com.incerpay.incerceller.domain.CardCompany;
import com.incerpay.incerceller.domain.PaymentMethod;
import com.incerpay.incerceller.domain.Seller;
import com.incerpay.incerceller.mapper.ApikeyInfoMapper;
import com.incerpay.incerceller.mapper.SellerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
	public void updateSellerCardList(Long sellerId, List<CardCompany> cardCompanies, List<PaymentMethod> paymentMethods) {
		SellerEntity seller = sellerRepository.findById(sellerId)
				.orElseThrow(() -> new IllegalArgumentException("상점을 찾을 수 없습니다."));
		seller.updateCardList(cardCompanies,paymentMethods);
	}

	@Override
	public void saveSeller(Seller seller) {
		sellerRepository.findById(seller.getSellerId())
				.ifPresent(s -> { throw new IllegalArgumentException("기 가입 사용자 아이디입니다."); });

		sellerRepository.save(sellerMapper.toSaveEntity(seller));
	}

}
