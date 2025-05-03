package com.github.maitmus.pcgspring.parkingTransaction.v1.dto;

import com.github.maitmus.pcgspring.parkingTransaction.v1.entity.ParkingTransaction;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CurrentParkingTransactionDetail extends ParkingTransactionDetail {
    private long currentParkingTime;
    private long currentChargeTime;
    private Integer expectingTotalAmount;

    public CurrentParkingTransactionDetail(ParkingTransaction transaction, int chargingFeePerSecond, int parkingFeePerMinute) {
        super(transaction);

        LocalDateTime now = LocalDateTime.now();
        this.currentParkingTime = Duration.between(transaction.getEntryTime(), now).toSeconds();
        this.expectingTotalAmount = transaction.getCurrentChargeAmount(chargingFeePerSecond) + transaction.getCurrentParkingAmount(parkingFeePerMinute);

        if (transaction.getChargeStartTime() == null) {
            this.currentChargeTime = 0;
        }
        else if (transaction.getChargeEndTime() != null) {
            this.currentChargeTime = Duration.between(transaction.getChargeStartTime(), transaction.getChargeEndTime()).toSeconds();
        }
        else {
            this.currentChargeTime = Duration.between(transaction.getChargeStartTime(), now).toSeconds();
        }
    }
}
