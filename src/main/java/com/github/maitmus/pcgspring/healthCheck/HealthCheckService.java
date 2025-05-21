package com.github.maitmus.pcgspring.healthCheck;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HealthCheckService {
    private final HealthCheckRepository healthCheckRepository;

    public void checkDbHealth() {
        healthCheckRepository.checkHealth();
    }
}
