package com.github.maitmus.pcgspring.parkingTransaction.v1.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkingTransactionRequest {
    @NotBlank
    private String carNumber;
}
