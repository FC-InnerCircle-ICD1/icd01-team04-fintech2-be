package com.incerpay.incerceller.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Seller {
	private Long sellerId;
	private String sellerName;
	private Long adminId;
	private List<ApiKeyInfo> apiKeyInfos;
}
