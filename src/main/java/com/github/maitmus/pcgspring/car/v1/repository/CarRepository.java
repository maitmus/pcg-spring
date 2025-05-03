package com.github.maitmus.pcgspring.car.v1.repository;

import com.github.maitmus.pcgspring.car.v1.entity.Car;
import com.github.maitmus.pcgspring.common.constant.EntityStatus;
import com.github.maitmus.pcgspring.user.v1.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends CrudRepository<Car, Long> {
    Optional<Car> findByCarNumberAndStatus(String carNumber, EntityStatus status);
    List<Car> findByUserAndStatus(User user, EntityStatus status);

    Optional<Car> findByIdAndUserAndStatus(Long id, User user, EntityStatus status);
}
