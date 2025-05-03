package com.github.maitmus.pcgspring.portOne.service;

import com.github.maitmus.pcgspring.card.v1.dto.PortOneAccessTokenRequest;
import com.github.maitmus.pcgspring.card.v1.dto.PortOneAccessTokenResponse;
import com.github.maitmus.pcgspring.card.v1.dto.PortOneGeneralResponse;
import com.github.maitmus.pcgspring.webclient.service.WebclientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PortOneService {
    private final WebclientService webclientService;

    @Value("${portone.api.url}")
    private String billingApiUrl;

    @Value("${portone.api.key}")
    private String portOneApiKey;

    @Value("${portone.api.secret}")
    private String portOneApiSecret;

    public String getAccessToken() {
        PortOneAccessTokenRequest requestBody = new PortOneAccessTokenRequest(
                portOneApiKey,
                portOneApiSecret
        );

        PortOneGeneralResponse<PortOneAccessTokenResponse> accessTokenResponse =
                webclientService.sendRequest(
                        HttpMethod.POST,
                        billingApiUrl + "/users/getToken",
                        requestBody,
                        new ParameterizedTypeReference<>() {}
                );

        return accessTokenResponse.getResponse().getAccessToken();
    }
}
