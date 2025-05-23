package com.github.maitmus.pcgspring.park.v1.controller;

import com.github.maitmus.pcgspring.common.dto.CommonResponse;
import com.github.maitmus.pcgspring.park.v1.service.PublicParkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "주차장")
@RestController
@RequestMapping("/v1/public/park")
@RequiredArgsConstructor
public class PublicParkController {
    private final PublicParkService publicParkService;

    @GetMapping
    @Operation(summary = "주차장 조회(5Km 이내)", description = "local에서는 위/경도 제공 시 작동하지 않음, 위/경도가 전부 null일 경우 전체 주차장 목록 반환")
    public CommonResponse<?> getParks(@RequestParam(required = false) Double lat,
                                      @RequestParam(required = false) Double lon,
                                      @RequestParam(required = false) String searchKeyword) {
        return publicParkService.getParksByCoordinate(lat, lon, searchKeyword);
    }
}
