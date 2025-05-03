package com.github.maitmus.pcgspring.car.v1.service;

import com.github.maitmus.pcgspring.car.v1.dto.*;
import com.github.maitmus.pcgspring.car.v1.entity.Car;
import com.github.maitmus.pcgspring.car.v1.repository.CarRepository;
import com.github.maitmus.pcgspring.common.constant.EntityStatus;
import com.github.maitmus.pcgspring.common.dto.CommonResponse;
import com.github.maitmus.pcgspring.excpetion.NotFoundException;
import com.github.maitmus.pcgspring.parkingTransaction.v1.entity.ParkingTransaction;
import com.github.maitmus.pcgspring.parkingTransaction.v1.repository.ParkingTransactionRepository;
import com.github.maitmus.pcgspring.user.v1.dto.UserDetails;
import com.github.maitmus.pcgspring.user.v1.entity.User;
import com.github.maitmus.pcgspring.user.v1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final ParkingTransactionRepository parkingTransactionRepository;
    private final UserService userService;

    @Transactional
    public CommonResponse<CreateCarResponse> createCar(CreateCarRequest request, UserDetails userDetails) {
        User user = userService.findByIdOrElseThrow(userDetails.getId());
        Car car = new Car(
                request.getCarNumber(),
                user
        );

        Car newCar = carRepository.save(car);

        List<ParkingTransaction> targetTransactions = parkingTransactionRepository.findByCarNumberAndStatus(car.getCarNumber(), EntityStatus.ACTIVE);

        targetTransactions.forEach(transaction -> transaction.linkCarInfo(user, newCar));

        parkingTransactionRepository.saveAll(targetTransactions);

        return new CommonResponse<>(new CreateCarResponse(newCar.getId()));
    }

    @Transactional(readOnly = true)
    public CommonResponse<CarDetails> getCar(UserDetails userDetails) {
        User user = userService.findByIdOrElseThrow(userDetails.getId());

        List<Car> cars = carRepository.findByUserAndStatus(user, EntityStatus.ACTIVE);

        return new CommonResponse<>(new CarDetails(cars.stream().map(CarDetail::new).toList()));
    }

    @Transactional
    public CommonResponse<?> deleteCar(Long id, UserDetails userDetails) {
        User user = userService.findByIdOrElseThrow(userDetails.getId());

        Car car = carRepository.findByIdAndUserAndStatus(id, user, EntityStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException("Car not found, id: " + id));

        car.delete();

        carRepository.save(car);

        return new CommonResponse<>(new DeleteCarResponse(id));
    }
}
