package com.incerpay.incerceller.adapter.out.persistence.jpa.entity;

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

	private Long adminId;

	@OneToMany
	@JoinColumn(name = "seller_id")  // 외래 키를 명시적으로 설정
	private List<ApiKeyInfoEntity> apiKeyInfos;  // 엔티티 타입으로 수정

}
