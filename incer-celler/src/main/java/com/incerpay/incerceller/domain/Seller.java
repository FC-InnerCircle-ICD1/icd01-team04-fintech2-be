package com.incerpay.incerceller.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class Seller {
	private Long sellerId;
	private String sellerName;
	private Long userId;
	private Boolean approveStatus;
	private LocalDateTime approveDate;
	private List<ApiKeyInfo> apiKeyInfos;
}
