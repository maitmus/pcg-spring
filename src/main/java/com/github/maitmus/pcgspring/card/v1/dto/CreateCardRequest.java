package com.github.maitmus.pcgspring.card.v1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;

@Getter
@NoArgsConstructor
public class CreateCardRequest {
    @CreditCardNumber
    private String cardNumber;
    @NotBlank
    @Size(min = 2, max = 2)
    private String expiryMonth;
    @NotBlank
    @Size(min = 4, max = 4)
    private String expiryYear;
    @NotBlank
    @Size(min = 2, max = 2)
    private String passwordFirstTwoDigits;
    @NotBlank
    private String nickname;
    @NotNull
    private Boolean isRepresentative;
}
