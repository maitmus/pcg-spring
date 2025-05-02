package com.github.maitmus.pcgspring.auth.v1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResetPasswordRequest {
    @NotBlank
    @Size(min = 6, max = 6)
    private String token;

    @NotBlank
    private String password;
}
