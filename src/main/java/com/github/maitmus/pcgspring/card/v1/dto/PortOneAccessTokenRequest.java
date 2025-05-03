package com.github.maitmus.pcgspring.card.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortOneAccessTokenRequest {
    private String imp_key;
    private String imp_secret;
}
