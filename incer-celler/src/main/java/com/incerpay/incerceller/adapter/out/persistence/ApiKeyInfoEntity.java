package com.incerpay.incerceller.adapter.out.persistence;

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

    // todo: 토스에선 왜 버전관리를?
   // private Long apiVersion;

}
