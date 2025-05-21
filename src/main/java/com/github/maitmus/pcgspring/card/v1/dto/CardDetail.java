package com.github.maitmus.pcgspring.card.v1.dto;

import com.github.maitmus.pcgspring.card.v1.entity.Card;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardDetail {
    private Long id;
    private String lastFourDigits;
    private Boolean isRepresentative;
    private String nickname;

    public CardDetail(Card card) {
        this.id = card.getId();
        this.lastFourDigits = card.getLastFourDigits();
        this.isRepresentative = card.getIsRepresentative();
        this.nickname = card.getNickname();
    }
}
