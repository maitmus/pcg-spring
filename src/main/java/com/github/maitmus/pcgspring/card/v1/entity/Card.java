package com.github.maitmus.pcgspring.card.v1.entity;

import com.github.maitmus.pcgspring.common.entity.BaseEntity;
import com.github.maitmus.pcgspring.user.v1.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cards")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Card extends BaseEntity {
    @Column(nullable = false)
    private String customerId;

    @Column(nullable = false)
    private String lastFourDigits;

    @Column(nullable = false)
    private Boolean isRepresentative;

    @Column(nullable = false)
    private String nickname;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public void unsetRepresentative() {
        this.isRepresentative = false;
    }

    public void setRepresentative() {
        this.isRepresentative = true;
    }

    public void setDecryptedCustomerId(String decryptedCustomerId) {
        this.customerId = decryptedCustomerId;
    }


}
