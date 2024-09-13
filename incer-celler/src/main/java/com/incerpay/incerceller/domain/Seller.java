package com.incerpay.incerceller.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Seller {
	private Long sellerId;
	private Long customerId;
	private String sellerName;
	private List<ApiKeyInfo> apiKeyInfos;
}
