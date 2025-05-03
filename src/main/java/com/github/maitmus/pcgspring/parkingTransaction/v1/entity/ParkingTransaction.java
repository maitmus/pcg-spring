package com.github.maitmus.pcgspring.parkingTransaction.v1.entity;

import com.github.maitmus.pcgspring.car.v1.entity.Car;
import com.github.maitmus.pcgspring.common.entity.BaseEntity;
import com.github.maitmus.pcgspring.park.v1.entity.Park;
import com.github.maitmus.pcgspring.user.v1.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "parking_transactions")
@NoArgsConstructor
public class ParkingTransaction extends BaseEntity {
    @Column
    private LocalDateTime entryTime;
    @Column
    private LocalDateTime exitTime;
    @Column
    private LocalDateTime chargeStartTime;
    @Column
    private LocalDateTime chargeEndTime;
    @Column
    private LocalDateTime paymentTime;
    @Column
    private Integer chargeAmount;
    @Column
    private Integer parkingAmount;
    @Column
    private Integer totalAmount;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Park park;
    @ManyToOne(fetch = FetchType.LAZY)
    private Car car;
    @Column
    private String carNumber; //비회원
    @Column
    private Boolean isPaid;
    @Column
    private UUID paymentId;

    public ParkingTransaction(
            User user,
            Car car,
            Park park,
            String carNumber,
            UUID paymentId
    ) {
        this.user = user;
        this.car = car;
        this.park = park;
        this.paymentId = paymentId;
        this.carNumber = carNumber;
        this.isPaid = false;
        this.entryTime = LocalDateTime.now();
    }

    public void exit() {
        this.exitTime = LocalDateTime.now();
    }

    public void setUnpaidTransaction() {
        this.parkingAmount = null;
        this.totalAmount = null;

        recordPaymentInfos();
    }

    public void startCharge() {
        this.chargeStartTime = LocalDateTime.now();
    }

    public void finishCharge(Integer chargingFeeInSeconds) {
        LocalDateTime chargeEndTime = LocalDateTime.now();
        int chargeAmount = getCurrentChargeAmount(chargingFeeInSeconds);

        this.chargeEndTime = chargeEndTime;
        this.chargeAmount = chargeAmount;
    }

    public int getCurrentChargeAmount(Integer chargingFeePerSecond) {
        if (chargeStartTime == null) {
            return 0;
        }
        LocalDateTime chargeEndTime = this.chargeEndTime != null ? this.chargeEndTime : LocalDateTime.now();
        long chargeTimeInSeconds = Duration.between(chargeStartTime, chargeEndTime).toSeconds();
        return Math.toIntExact(chargeTimeInSeconds * chargingFeePerSecond);
    }

    public int getCurrentParkingAmount(Integer parkingFeePerMinute) {
        LocalDateTime exitTime = LocalDateTime.now();
        long parkingTimeInMinutes = Duration.between(entryTime, exitTime).toMinutes();
        return Math.toIntExact(parkingTimeInMinutes * parkingFeePerMinute);
    }

    public void completePayment(Integer totalAmount, Integer parkingAmount) {
        this.totalAmount = totalAmount;
        this.parkingAmount = parkingAmount;

        recordPaymentInfos();
    }

    private void recordPaymentInfos() {
        this.isPaid = true;
        this.paymentTime = LocalDateTime.now();
    }

    public void linkCarInfo(User user, Car car) {
        this.user = user;
        this.car = car;
    }
}
