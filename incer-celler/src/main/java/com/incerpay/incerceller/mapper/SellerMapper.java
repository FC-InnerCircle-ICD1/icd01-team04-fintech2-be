package com.incerpay.incerceller.mapper;

import com.incerpay.incerceller.adapter.out.persistence.jpa.entity.ApiKeyInfoEntity;
import com.incerpay.incerceller.adapter.out.persistence.jpa.entity.SellerEntity;
import com.incerpay.incerceller.domain.Seller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
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

		List<ApiKeyInfoEntity> apiKeyInfoEntities = List.of();

		if (seller.getApiKeyInfos() != null && !seller.getApiKeyInfos().isEmpty()) {
			apiKeyInfoEntities = seller.getApiKeyInfos().stream()
					.map(apikeyInfoMapper::toEntity)
					.toList();
		}

		return SellerEntity.builder()
				.sellerId(seller.getSellerId())
				.sellerName(seller.getSellerName())
				.apiKeyInfos(apiKeyInfoEntities)
				.paymentMethods(seller.getPaymentMethods())
				.cardCompanies(seller.getCardCompanies())
				.build();
	}

	public Seller toDomain(SellerEntity sellerEntity) {
		return Seller.builder()
				.sellerName(sellerEntity.getSellerName())
				.sellerId(sellerEntity.getSellerId())
				.paymentMethods(sellerEntity.getPaymentMethods())
				.cardCompanies(sellerEntity.getCardCompanies())
				.apiKeyInfos(sellerEntity.getApiKeyInfos().stream()
						.map(apikeyInfoMapper::toDomain).toList())
				.build();
	}

}
