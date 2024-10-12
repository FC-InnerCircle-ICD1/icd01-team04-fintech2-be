package com.incerpay.incerceller.adapter.out.persistence.jpa.entity;

import com.incerpay.incerceller.domain.CardCompany;
import com.incerpay.incerceller.domain.PaymentMethod;
import com.incerpay.incerceller.util.StringListConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SellerEntity extends BaseEntity {
	@Id
	private Long sellerId;

	private String sellerName;

	@Convert(converter = StringListConverter.class)  // JSON 변환기 사용
	private List<CardCompany> cardCompanies;
	@Convert(converter = StringListConverter.class)  // JSON 변환기 사용
	private List<PaymentMethod> paymentMethods;

	@OneToMany
	@JoinColumn(name = "seller_id")  // 외래 키를 명시적으로 설정
	private List<ApiKeyInfoEntity> apiKeyInfos;  // 엔티티 타입으로 수정

	public void addApiKeyInfo(ApiKeyInfoEntity apiKeyInfo) {
		apiKeyInfos.add(apiKeyInfo);
	}

}
