package com.github.maitmus.pcgspring.car.v1.dto;

import com.github.maitmus.pcgspring.car.v1.entity.Car;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CarDetail {
    private Long id;
    private String carNumber;

    public CarDetail(Car car) {
        this.id = car.getId();
        this.carNumber = car.getCarNumber();
    }
}
