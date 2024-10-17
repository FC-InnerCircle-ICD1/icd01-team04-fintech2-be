package com.incerpay.incerceller.application.service;

import com.incerpay.incerceller.application.dto.CardRegisterRequest;
import com.incerpay.incerceller.application.port.in.AssignCardUseCase;
import com.incerpay.incerceller.application.port.in.AssignSellerUseCase;
import com.incerpay.incerceller.application.port.in.GetSellerUseCase;
import com.incerpay.incerceller.application.port.out.SaveSellerPort;
import com.incerpay.incerceller.application.port.out.SelectSellerPort;
import com.incerpay.incerceller.domain.Seller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SellerService implements GetSellerUseCase, AssignSellerUseCase, AssignCardUseCase {

	private final SelectSellerPort selectSellerPort;
	private final SaveSellerPort saveSellerPort;

	@Override
	public Seller getSeller(Long sellerId) {
		return getSellerOrThrow(sellerId);
	}

	@Override
	@Transactional
	public void assignSeller(Long sellerId, String sellerName) {

		if(getOptionalSeller(sellerId).isPresent()) {
			throw new IllegalArgumentException("기 가입 사용자 아이디입니다.");
		}

		saveSellerPort.saveSeller(Seller.builder()
				.sellerId(sellerId)
				.sellerName(sellerName).build());


	}

	@Override
	@Transactional
	public void assignCard(CardRegisterRequest request) {

		Seller seller = getSellerOrThrow(request.sellerId());

		saveSellerPort.saveSeller(Seller.builder()
				.sellerId(request.sellerId())
				.sellerName(seller.getSellerName())
				.apiKeyInfos(seller.getApiKeyInfos())
				.paymentMethods(request.paymentMethod())
				.cardCompanies(request.cardCompany()).build());
	}

	private Optional<Seller> getOptionalSeller(Long sellerId) {

		try{
			return Optional.ofNullable(selectSellerPort.selectSeller(sellerId));
		} catch (IllegalArgumentException ex) {
			return Optional.empty();
		}

	}

	private Seller getSellerOrThrow(Long sellerId) {
		return selectSellerPort.selectSeller(sellerId);
	}

}
