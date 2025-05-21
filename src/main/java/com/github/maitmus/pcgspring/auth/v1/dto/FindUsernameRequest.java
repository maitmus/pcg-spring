package com.github.maitmus.pcgspring.auth.v1.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FindUsernameRequest {
    @NotBlank
    @Size(min = 2, max = 20)
    private String name;
    @Email
    private String email;
}
