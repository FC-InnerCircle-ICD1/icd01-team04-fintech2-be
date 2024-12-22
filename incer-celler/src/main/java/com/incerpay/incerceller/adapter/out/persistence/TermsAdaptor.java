package com.incerpay.incerceller.adapter.out.persistence;

import com.incerpay.incerceller.adapter.out.persistence.jpa.repository.TermsRepository;
import com.incerpay.incerceller.application.port.out.SelectTermsPort;
import com.incerpay.incerceller.domain.*;
import com.incerpay.incerceller.mapper.TermsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TermsAdaptor implements SelectTermsPort {

	private final TermsRepository termsRepository;
	private final TermsMapper termsMapper;


	@Override
	public Terms selectTerms() {
		return termsMapper.toDomain(termsRepository.findLatestByVersion()
				.orElseThrow(() -> new IllegalArgumentException("약관을 찾을 수 없습니다.")));
	}
}
