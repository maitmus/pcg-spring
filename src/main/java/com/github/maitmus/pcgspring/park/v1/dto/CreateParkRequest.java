package com.github.maitmus.pcgspring.park.v1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateParkRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String phone;
    @NotBlank
    private String address;
    @NotNull
    private Integer disabilitySpace;
    @NotBlank
    private String manageCode;
    @NotNull
    private Double lat;
    @NotNull
    private Double lon;
    @NotBlank
    private String ip;
}
