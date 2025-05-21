package com.github.maitmus.pcgspring.park.v1.controller;

import com.github.maitmus.pcgspring.common.dto.CommonResponse;
import com.github.maitmus.pcgspring.park.v1.dto.CreateParkRequest;
import com.github.maitmus.pcgspring.park.v1.service.ParkService;
import com.github.maitmus.pcgspring.user.v1.dto.UserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "주차장")
@RestController
@RequestMapping("/v1/park")
@RequiredArgsConstructor
public class ParkController {
    private final ParkService parkService;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "주차장 생성 요청")
    public CommonResponse<?> createPark(@RequestBody @Valid CreateParkRequest request,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        return parkService.createPark(request, userDetails);
    }
}
