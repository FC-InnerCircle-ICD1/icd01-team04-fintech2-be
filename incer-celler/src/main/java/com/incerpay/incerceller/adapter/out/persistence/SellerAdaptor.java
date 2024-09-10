package com.incerpay.incerceller.adapter.out.persistence;

import com.incerpay.incerceller.adapter.out.persistence.jpa.entity.ApiKeyInfoEntity;
import com.incerpay.incerceller.adapter.out.persistence.jpa.repository.ApiKeyInfoRepository;
import com.incerpay.incerceller.adapter.out.persistence.jpa.repository.SellerRepository;
import com.incerpay.incerceller.application.port.out.SaveLiveApiKeyPort;
import com.incerpay.incerceller.application.port.out.SelectSellerPort;
import com.incerpay.incerceller.domain.Seller;
import com.incerpay.incerceller.mapper.SellerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class SellerAdaptor implements SelectSellerPort {

    private final SellerRepository sellerRepository;
    private final SellerMapper sellerMapper;

    @Override
    public Seller selectSeller(Long sellerId) {
        return sellerMapper.toDomain(sellerRepository.findById(sellerId)
                .orElseThrow(() -> new IllegalArgumentException("상점을 찾을 수 없습니다.")));
    }

    @Override
    public List<Seller> selectSellers(Long adminId) {
        return sellerRepository.findAllByAdminId(adminId)
                .stream().map(sellerMapper::toDomain).toList();
    }

}
