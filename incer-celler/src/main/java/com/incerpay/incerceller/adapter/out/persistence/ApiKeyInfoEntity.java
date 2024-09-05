package com.incerpay.incerceller.adapter.out.persistence;

import com.incerpay.incerceller.common.domain.BaseEntity;
import com.incerpay.incerceller.domain.ApiKeyState;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ApiKeyInfoEntity extends BaseEntity {
    @Id
    private String apiKey;

    private String sellerId;

    @Enumerated(EnumType.STRING)
    private ApiKeyState apiKeyState;

}
