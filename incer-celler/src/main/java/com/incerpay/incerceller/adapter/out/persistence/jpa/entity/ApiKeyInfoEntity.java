package com.incerpay.incerceller.adapter.out.persistence.jpa.entity;

import com.incerpay.incerceller.domain.ApiKeyState;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ApiKeyInfoEntity extends BaseEntity {
    @Id
    private String apiKey;

    @Enumerated(EnumType.STRING)
    private ApiKeyState apiKeyState;

    // todo: 토스에선 왜 버전관리를?
   // private Long apiVersion;

}
