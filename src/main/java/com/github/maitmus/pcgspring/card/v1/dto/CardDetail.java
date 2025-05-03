package com.github.maitmus.pcgspring.card.v1.dto;

import com.github.maitmus.pcgspring.card.v1.entity.Card;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardDetail {
    private Long id;
    private UUID customerId;
    private Boolean isRepresentative;
    private String nickname;

    public CardDetail(Card card) {
        this.id = card.getId();
        this.customerId = UUID.fromString(card.getCustomerId());
        this.isRepresentative = card.getIsRepresentative();
        this.nickname = card.getNickname();
    }
}
