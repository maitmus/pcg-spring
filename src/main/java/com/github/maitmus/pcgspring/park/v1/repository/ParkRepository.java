package com.github.maitmus.pcgspring.park.v1.repository;

import com.github.maitmus.pcgspring.common.constant.EntityStatus;
import com.github.maitmus.pcgspring.park.v1.entity.Park;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkRepository extends JpaRepository<Park, Long> {
    Optional<Park> findByManageCodeAndStatus(String manageCode, EntityStatus status);
}
