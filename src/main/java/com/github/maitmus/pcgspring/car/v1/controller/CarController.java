package com.github.maitmus.pcgspring.car.v1.controller;

import com.github.maitmus.pcgspring.car.v1.dto.CarDetails;
import com.github.maitmus.pcgspring.car.v1.dto.CreateCarRequest;
import com.github.maitmus.pcgspring.car.v1.dto.CreateCarResponse;
import com.github.maitmus.pcgspring.car.v1.service.CarService;
import com.github.maitmus.pcgspring.common.dto.CommonResponse;
import com.github.maitmus.pcgspring.user.dto.UserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/car")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @PostMapping
    public CommonResponse<CreateCarResponse> createCar(@RequestBody @Valid CreateCarRequest request,
                                                       @AuthenticationPrincipal UserDetails userDetails) {
        return carService.createCar(request, userDetails);
    }

    @GetMapping
    public CommonResponse<CarDetails> getCar(@AuthenticationPrincipal UserDetails userDetails) {
        return carService.getCar(userDetails);
    }

    @DeleteMapping("/{id}")
    public CommonResponse<?> deleteCar(@AuthenticationPrincipal UserDetails userDetails,
                                       @PathVariable Long id) {
        return carService.deleteCar(id, userDetails);
    }
}
