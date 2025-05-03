package com.github.maitmus.pcgspring.auth.v1.repository;

import com.github.maitmus.pcgspring.auth.v1.entity.EmailToken;
import com.github.maitmus.pcgspring.common.constant.EntityStatus;
import com.github.maitmus.pcgspring.user.v1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailTokenRepository extends JpaRepository<EmailToken, Long> {
    Optional<EmailToken> findByUserAndStatus(User user, EntityStatus entityStatus);
}
