package com.github.maitmus.pcgspring.card.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PortOneAccessTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;
    private Integer now;
    @JsonProperty("expired_at")
    private Integer expiredAt;
}
