package com.github.maitmus.pcgspring.user.v1.repository;

import com.github.maitmus.pcgspring.common.constant.EntityStatus;
import com.github.maitmus.pcgspring.user.v1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdAndStatus(Long id, EntityStatus status);

    Optional<User> findByUsernameAndStatus(String username, EntityStatus status);
}
