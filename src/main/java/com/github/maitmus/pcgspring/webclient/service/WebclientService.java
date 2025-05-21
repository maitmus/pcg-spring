package com.github.maitmus.pcgspring.webclient.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class WebclientService {
    private final WebClient webClient;

    public <T> T sendRequest(
        HttpMethod method,
        String uri,
        @Nullable Object requestBody,
        ParameterizedTypeReference<T> responseType
    ) {
        WebClient.RequestBodySpec requestSpec = webClient
            .method(method)
            .uri(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);

        if (requestBody != null) {
            requestSpec = (WebClient.RequestBodySpec) requestSpec.bodyValue(requestBody);
        }

        return requestSpec
            .retrieve()
            .bodyToMono(responseType)
            .block();
    }

    public <T> T sendFormDataRequest(
        HttpMethod method,
        String uri,
        MultiValueMap<String, String> formData,
        Class<T> responseType,
        String accessToken
    ) {
        return webClient
            .method(method)
            .uri(uri)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .header(HttpHeaders.AUTHORIZATION, accessToken)
            .body(BodyInserters.fromFormData(formData))
            .retrieve()
            .bodyToMono(responseType)
            .block();
    }
}