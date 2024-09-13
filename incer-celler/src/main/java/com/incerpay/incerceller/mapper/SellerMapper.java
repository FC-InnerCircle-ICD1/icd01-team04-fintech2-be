package com.incerpay.incerceller.mapper;

import com.incerpay.incerceller.adapter.out.persistence.jpa.entity.SellerEntity;
import com.incerpay.incerceller.domain.Seller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SellerMapper {

	private final ApikeyInfoMapper apikeyInfoMapper;

	public SellerEntity toEntity(Seller seller) {
		return SellerEntity.builder()
				.sellerName(seller.getSellerName())
				.apiKeyInfos(seller.getApiKeyInfos().stream()
						.map(apikeyInfoMapper::toEntity).toList())
				.build();
	}

	public SellerEntity toSaveEntity(Seller seller) {
		return SellerEntity.builder()
				.sellerId(seller.getSellerId())
				.sellerName(seller.getSellerName())
				.build();
	}

	public Seller toDomain(SellerEntity sellerEntity) {
		return Seller.builder()
				.sellerName(sellerEntity.getSellerName())
				.sellerId(sellerEntity.getSellerId())
				.apiKeyInfos(sellerEntity.getApiKeyInfos().stream()
						.map(apikeyInfoMapper::toDomain).toList())
				.build();
	}

}
