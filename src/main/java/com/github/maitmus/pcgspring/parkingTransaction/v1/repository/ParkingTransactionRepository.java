package com.github.maitmus.pcgspring.parkingTransaction.v1.repository;

import com.github.maitmus.pcgspring.common.constant.EntityStatus;
import com.github.maitmus.pcgspring.park.v1.entity.Park;
import com.github.maitmus.pcgspring.parkingTransaction.v1.entity.ParkingTransaction;
import com.github.maitmus.pcgspring.user.v1.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParkingTransactionRepository extends JpaRepository<ParkingTransaction, Long> {
    boolean existsByCarNumberAndExitTimeIsNull(@NotBlank String carNumber);
    Optional<ParkingTransaction> findByParkAndCarNumberAndExitTimeIsNull(Park park, String carNumber);
    Optional<ParkingTransaction> findByPaymentIdAndStatusAndIsPaidIsFalse(@NotNull UUID paymentId, EntityStatus status);
    List<ParkingTransaction> findByCarNumberAndStatus(String carNumber, EntityStatus status);
    List<ParkingTransaction> findByUserAndStatusAndIsPaidIsFalse(User user, EntityStatus status);
    Slice<ParkingTransaction> findByUserAndStatus(User user, EntityStatus entityStatus, Pageable pageable);
    Optional<ParkingTransaction> findByCarNumberAndParkAndStatus(String carNumber, Park park, EntityStatus status);
}
