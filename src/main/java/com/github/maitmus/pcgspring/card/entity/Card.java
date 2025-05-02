package com.github.maitmus.pcgspring.card.entity;

import com.github.maitmus.pcgspring.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Card extends BaseEntity {
    @Column(length = 100, unique = true, nullable = false)
    private String cardNumber;

    @Column(length = 2, nullable = false)
    private String expiryMonth;

    @Column(length = 2, nullable = false)
    private String expiryYear;
}
