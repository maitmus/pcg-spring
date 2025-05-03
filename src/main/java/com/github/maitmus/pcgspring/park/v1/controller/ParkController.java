package com.github.maitmus.pcgspring.park.v1.controller;

import com.github.maitmus.pcgspring.common.dto.CommonResponse;
import com.github.maitmus.pcgspring.park.v1.dto.CreateParkRequest;
import com.github.maitmus.pcgspring.park.v1.entity.Park;
import com.github.maitmus.pcgspring.park.v1.service.ParkService;
import com.github.maitmus.pcgspring.user.dto.UserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/park")
@RequiredArgsConstructor
public class ParkController {
    private final ParkService parkService;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CommonResponse<?> createPark(@RequestBody @Valid CreateParkRequest request,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        return parkService.createPark(request, userDetails);
    }
}
