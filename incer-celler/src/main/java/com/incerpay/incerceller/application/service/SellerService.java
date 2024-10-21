package com.incerpay.incerceller.application.service;

import com.incerpay.incerceller.application.dto.CardRegisterRequest;
import com.incerpay.incerceller.application.port.in.AssignCardUseCase;
import com.incerpay.incerceller.application.port.in.AssignSellerUseCase;
import com.incerpay.incerceller.application.port.in.GetSellerUseCase;
import com.incerpay.incerceller.application.port.out.SaveSellerPort;
import com.incerpay.incerceller.application.port.out.SelectSellerPort;
import com.incerpay.incerceller.application.port.out.UpdateSellerPort;
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
    private final UpdateSellerPort updateSellerPort;

    @Override
    public Seller getSeller(Long sellerId) {
        return getSellerOrThrow(sellerId);
    }

    @Override
    @Transactional
    public void assignSeller(Long sellerId, String sellerName) {
        saveSellerPort.saveSeller(Seller.builder()
                .sellerId(sellerId)
                .sellerName(sellerName).build());
    }

    @Override
    @Transactional
    public void assignCard(CardRegisterRequest request) {
        //todo : sevice 레이어에선 커맨드 사용
        updateSellerPort.updateSellerCardList(request.sellerId(), request.cardCompany(), request.paymentMethod());
    }

    private Seller getSellerOrThrow(Long sellerId) {
        return selectSellerPort.selectSeller(sellerId);
    }

}
