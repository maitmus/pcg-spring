package com.github.maitmus.pcgspring.car.v1.controller;

import com.github.maitmus.pcgspring.car.v1.dto.CarDetails;
import com.github.maitmus.pcgspring.car.v1.dto.CreateCarRequest;
import com.github.maitmus.pcgspring.car.v1.dto.CreateCarResponse;
import com.github.maitmus.pcgspring.car.v1.service.CarService;
import com.github.maitmus.pcgspring.common.dto.CommonResponse;
import com.github.maitmus.pcgspring.user.v1.dto.UserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "자동차")
@RestController
@RequestMapping("/v1/car")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "자동차 등록")
    public CommonResponse<CreateCarResponse> createCar(@RequestBody @Valid CreateCarRequest request,
                                                       @AuthenticationPrincipal UserDetails userDetails) {
        return carService.createCar(request, userDetails);
    }

    @GetMapping
    @Operation(summary = "자동차 조회")
    public CommonResponse<CarDetails> getCar(@AuthenticationPrincipal UserDetails userDetails) {
        return carService.getCar(userDetails);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "자동차 삭제")
    public CommonResponse<?> deleteCar(@AuthenticationPrincipal UserDetails userDetails,
                                       @PathVariable Long id) {
        return carService.deleteCar(id, userDetails);
    }
}
