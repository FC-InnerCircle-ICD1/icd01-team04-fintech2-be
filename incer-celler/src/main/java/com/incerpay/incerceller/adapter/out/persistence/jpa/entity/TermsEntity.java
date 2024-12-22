package com.incerpay.incerceller.adapter.out.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "terms")
public class TermsEntity extends BaseEntity {

    @Id
    private Long id;

    @Column(name = "version")
    private String version;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

}
