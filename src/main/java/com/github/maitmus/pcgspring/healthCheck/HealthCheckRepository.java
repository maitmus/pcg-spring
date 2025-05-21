package com.github.maitmus.pcgspring.healthCheck;

import com.github.maitmus.pcgspring.user.v1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HealthCheckRepository extends JpaRepository<User, Long> {
    @Query("SELECT 1")
    Long checkHealth();
}
