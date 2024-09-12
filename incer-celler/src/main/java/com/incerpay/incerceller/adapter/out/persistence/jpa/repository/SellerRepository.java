package com.incerpay.incerceller.adapter.out.persistence.jpa.repository;

import com.incerpay.incerceller.adapter.out.persistence.jpa.entity.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerRepository extends JpaRepository<SellerEntity, Long> {

	List<SellerEntity> findAllByUserId(Long userId);

}
