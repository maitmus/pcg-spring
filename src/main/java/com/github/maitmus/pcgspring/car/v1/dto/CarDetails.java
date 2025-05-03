package com.github.maitmus.pcgspring.car.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CarDetails {
    List<CarDetail> cars;
}
