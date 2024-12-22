package com.incerpay.incerceller.adapter.out.persistence.jpa.repository;

import com.incerpay.incerceller.adapter.out.persistence.jpa.entity.TermsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TermsRepository extends JpaRepository<TermsEntity, Long> {

    @Query("SELECT t FROM TermsEntity t ORDER BY t.version DESC LIMIT 1")
    Optional<TermsEntity> findLatestByVersion();
}
