package com.github.maitmus.pcgspring.car.v1.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCarRequest {
    @NotBlank
    private String carNumber;
}
