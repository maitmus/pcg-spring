package com.github.maitmus.pcgspring.auth.v1.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CreateUserRequest extends LoginRequest {
    @NotBlank
    @Size(min = 2, max = 20)
    private String name;

    @NotBlank
    @Size(min = 3, max = 15)
    private String nickname;

    @Email
    private String email;

    @NotNull
    private LocalDate birth;

    public void setHashedPassword(String hashedPassword) {
        this.password = hashedPassword;
    }
}
