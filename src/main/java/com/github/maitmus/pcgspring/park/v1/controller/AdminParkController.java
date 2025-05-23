package com.github.maitmus.pcgspring.park.v1.controller;

import com.github.maitmus.pcgspring.common.dto.CommonResponse;
import com.github.maitmus.pcgspring.park.v1.service.AdminParkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "주차장")
@RestController
@RequestMapping("/v1/admin/park")
@RequiredArgsConstructor
public class AdminParkController {
    private final AdminParkService adminParkService;

    @GetMapping
    @Operation(summary = "주차장 생성 요청 목록 조회")
    public CommonResponse<?> getParkRegistrationRequests() {
        return adminParkService.getParkRegistrationRequests();
    }
}
