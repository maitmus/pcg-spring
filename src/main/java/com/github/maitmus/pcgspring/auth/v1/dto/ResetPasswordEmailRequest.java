package com.github.maitmus.pcgspring.auth.v1.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResetPasswordEmailRequest {
    @Email
    private String email;

    @NotBlank
    @Size(min = 5, max = 20)
    private String username;
}
