package com.github.maitmus.pcgspring.parkingTransaction.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingTransactionDetails {
    private List<ParkingTransactionDetail> parkingTransactions;
    private boolean hasNext;
}
