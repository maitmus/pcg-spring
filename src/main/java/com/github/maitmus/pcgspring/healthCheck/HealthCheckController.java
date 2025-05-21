package com.github.maitmus.pcgspring.healthCheck;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Health Check")
@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
public class HealthCheckController {
    private final HealthCheckService healthCheckService;

    @GetMapping
    @Operation(summary = "헬스 체크(서버)")
    public void checkHealth() {
    }

    @GetMapping("/db")
    @Operation(summary = "헬스 체크(DB)")
    public void checkDbHealth() {
        healthCheckService.checkDbHealth();
    }
}
