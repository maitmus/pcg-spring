package com.github.maitmus.pcgspring.auth.v1.repository;

import com.github.maitmus.pcgspring.common.constant.EntityStatus;
import com.github.maitmus.pcgspring.user.v1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndStatus(String username, EntityStatus status);

    boolean existsByUsername(String username);

    Optional<User> findByNameAndEmailAndStatus(String name, String email, EntityStatus status);

    Optional<User> findByEmailAndUsernameAndStatus(String email, String username, EntityStatus status);
}
