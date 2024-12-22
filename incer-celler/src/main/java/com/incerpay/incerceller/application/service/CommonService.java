package com.incerpay.incerceller.application.service;

import com.incerpay.incerceller.application.port.in.GetTermsUseCase;
import com.incerpay.incerceller.application.port.out.SelectTermsPort;
import com.incerpay.incerceller.domain.Terms;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommonService implements GetTermsUseCase {

	private final SelectTermsPort selectTermsPort;

	@Override
	public Terms getTerms() {
		return selectTermsPort.selectTerms();
	}

}
