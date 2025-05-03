package com.github.maitmus.pcgspring.payment.v1.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class PayRequest {
    @NotNull
    private UUID paymentId;
}
