package com.github.maitmus.pcgspring.parkingTransaction.v1.service;

import com.github.maitmus.pcgspring.car.v1.entity.Car;
import com.github.maitmus.pcgspring.car.v1.repository.CarRepository;
import com.github.maitmus.pcgspring.common.constant.EntityStatus;
import com.github.maitmus.pcgspring.common.dto.CommonResponse;
import com.github.maitmus.pcgspring.excpetion.ConflictException;
import com.github.maitmus.pcgspring.excpetion.ForbiddenException;
import com.github.maitmus.pcgspring.excpetion.NotFoundException;
import com.github.maitmus.pcgspring.park.v1.entity.Park;
import com.github.maitmus.pcgspring.park.v1.repository.ParkRepository;
import com.github.maitmus.pcgspring.parkingTransaction.v1.dto.*;
import com.github.maitmus.pcgspring.parkingTransaction.v1.entity.ParkingTransaction;
import com.github.maitmus.pcgspring.parkingTransaction.v1.repository.ParkingTransactionRepository;
import com.github.maitmus.pcgspring.user.v1.dto.UserDetails;
import com.github.maitmus.pcgspring.user.v1.entity.User;
import com.github.maitmus.pcgspring.user.v1.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParkingTransactionService {
    private final CarRepository carRepository;
    private final ParkRepository parkRepository;
    private final ParkingTransactionRepository parkingTransactionRepository;
    private final UserService userService;

    @Value("${charging.fee.per.second}")
    private Integer chargingFeePerSecond;

    @Value("${parking.fee.per.minute}")
    private Integer parkingFeePerMinute;

    @Transactional
    public CommonResponse<CreateParkingTransactionResponse> createParkingTransaction(ParkingTransactionRequest request, String manageCode) {
        Park park = findParkByManageCode(manageCode);

        Car car = carRepository.findByCarNumberAndStatus(request.getCarNumber(), EntityStatus.ACTIVE)
                .orElse(null);

        if (parkingTransactionRepository.existsByCarNumberAndExitTimeIsNull(request.getCarNumber())) {
            throw new ConflictException("Car already parked, carNumber: " + request.getCarNumber());
        }

        UUID paymentId = UUID.randomUUID();

        ParkingTransaction parkingTransaction = new ParkingTransaction(
                car != null ? car.getUser() : null,
                car,
                park,
                car != null ? car.getCarNumber() : request.getCarNumber(),
                paymentId
        );

        ParkingTransaction newParkingTransaction = parkingTransactionRepository.save(parkingTransaction);

        return new CommonResponse<>(new CreateParkingTransactionResponse(newParkingTransaction.getPaymentId()));
    }

    @Transactional
    public CommonResponse<?> exitParkingTransaction(@Valid ParkingTransactionRequest request, String manageCode) {
        Park park = findParkByManageCode(manageCode);

        ParkingTransaction parkingTransaction = findByParkAndCarNumberAndNotExited(request.getCarNumber(), park);

        if (!parkingTransaction.getIsPaid()) {
            throw new ForbiddenException("Payment is not completed, paymentId: " + parkingTransaction.getPaymentId());
        }

        parkingTransaction.exit();

        parkingTransactionRepository.save(parkingTransaction);

        return new CommonResponse<>();
    }

    @Transactional
    public CommonResponse<?> startCharge(@Valid ParkingTransactionRequest request, String manageCode) {
        Park park = findParkByManageCode(manageCode);

        ParkingTransaction parkingTransaction = findByParkAndCarNumberAndNotExited(request.getCarNumber(), park);

        if (parkingTransaction.getChargeStartTime() != null) {
            throw new ConflictException("Car is already charging, carNumber: " + request.getCarNumber());
        }

        parkingTransaction.startCharge();

        parkingTransactionRepository.save(parkingTransaction);

        return new CommonResponse<>();
    }

    @Transactional
    public CommonResponse<?> finishCharge(@Valid ParkingTransactionRequest request, String manageCode) {
        Park park = findParkByManageCode(manageCode);

        ParkingTransaction parkingTransaction = findByParkAndCarNumberAndNotExited(request.getCarNumber(), park);

        if (parkingTransaction.getChargeStartTime() == null) {
            throw new ConflictException("Car is not charging, carNumber: " + request.getCarNumber());
        }

        if (parkingTransaction.getChargeEndTime() != null) {
            throw new ConflictException("Car is already charged, carNumber: " + request.getCarNumber());
        }

        parkingTransaction.finishCharge(chargingFeePerSecond);
        parkingTransactionRepository.save(parkingTransaction);

        return new CommonResponse<>();
    }

    @Transactional(readOnly = true)
    public CommonResponse<ParkingTransactionDetails> getParkingTransactions(UserDetails userDetails, Pageable pageable) {
        User user = userService.findByIdOrElseThrow(userDetails.getId());

        Slice<ParkingTransaction> transactions = parkingTransactionRepository.findByUserAndStatus(user, EntityStatus.ACTIVE, pageable);

        return new CommonResponse<>(new ParkingTransactionDetails(transactions.stream().map(ParkingTransactionDetail::new).toList(), transactions.hasNext()));
    }

    @Transactional(readOnly = true)
    public CommonResponse<CurrentParkingTransactionDetails> getCurrentParkingTransaction(UserDetails userDetails) {
        User user = userService.findByIdOrElseThrow(userDetails.getId());

        List<ParkingTransaction> transactions = parkingTransactionRepository.findByUserAndStatusAndIsPaidIsFalse(user, EntityStatus.ACTIVE);

        return new CommonResponse<>(new CurrentParkingTransactionDetails(transactions.stream().map(transaction -> new CurrentParkingTransactionDetail(transaction, chargingFeePerSecond, parkingFeePerMinute)).toList()));
    }

    private ParkingTransaction findByParkAndCarNumberAndNotExited(@NotBlank String carNumber, Park park) {
        return parkingTransactionRepository.findByParkAndCarNumberAndExitTimeIsNull(
                park,
                carNumber
        ).orElseThrow(() -> new ConflictException("Car is not parked, carNumber: " + carNumber));
    }

    private Park findParkByManageCode(String manageCode) {
        return parkRepository.findByManageCodeAndStatus(manageCode, EntityStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException("Park not found"));
    }


}
