package com.github.maitmus.pcgspring.parkingTransaction.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateParkingTransactionResponse {
    private UUID paymentId;
}
