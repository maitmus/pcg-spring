package com.github.maitmus.pcgspring.card.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardDetails {
    List<CardDetail> cards;
}
