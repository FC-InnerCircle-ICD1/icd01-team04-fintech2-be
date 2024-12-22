package com.incerpay.incerceller.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class Terms {

	private List<String> terms;

}
