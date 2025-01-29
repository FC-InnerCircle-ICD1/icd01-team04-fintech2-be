package com.incerpay.incerceller.mapper;


import com.incerpay.incerceller.adapter.out.persistence.jpa.entity.TermsEntity;
import com.incerpay.incerceller.domain.Terms;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class TermsMapper {

	public Terms toDomain(TermsEntity entity) {

		List <String> termsList = new ArrayList<>();
		termsList.add(entity.getContent());

		return Terms.builder().terms(termsList).build();
	}

}
