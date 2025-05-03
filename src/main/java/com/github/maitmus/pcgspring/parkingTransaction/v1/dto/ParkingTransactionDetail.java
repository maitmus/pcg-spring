package com.github.maitmus.pcgspring.parkingTransaction.v1.dto;

import com.github.maitmus.pcgspring.car.v1.dto.CarDetail;
import com.github.maitmus.pcgspring.car.v1.entity.Car;
import com.github.maitmus.pcgspring.park.v1.dto.ParkDetail;
import com.github.maitmus.pcgspring.park.v1.entity.Park;
import com.github.maitmus.pcgspring.parkingTransaction.v1.entity.ParkingTransaction;
import com.github.maitmus.pcgspring.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class ParkingTransactionDetail {
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private LocalDateTime chargeStartTime;
    private LocalDateTime chargeEndTime;
    private LocalDateTime paymentTime;
    private Integer chargeAmount;
    private Integer parkingAmount;
    private Integer totalAmount;
    private ParkDetail park;
    private CarDetail car;
    private String carNumber;
    private Boolean isPaid;
    private UUID paymentId;

    public ParkingTransactionDetail(ParkingTransaction transaction) {
        this.entryTime = transaction.getEntryTime();
        this.exitTime = transaction.getExitTime();
        this.chargeStartTime = transaction.getChargeStartTime();
        this.chargeEndTime = transaction.getChargeEndTime();
        this.paymentTime = transaction.getPaymentTime();
        this.chargeAmount = transaction.getChargeAmount();
        this.parkingAmount = transaction.getParkingAmount();
        this.totalAmount = transaction.getTotalAmount();
        this.carNumber = transaction.getCarNumber();
        this.isPaid = transaction.getIsPaid();
        this.paymentId = transaction.getPaymentId();

        this.park = new ParkDetail(transaction.getPark());

        if (transaction.getCar() != null) {
            this.car = new CarDetail(transaction.getCar());
        }
    }
}
