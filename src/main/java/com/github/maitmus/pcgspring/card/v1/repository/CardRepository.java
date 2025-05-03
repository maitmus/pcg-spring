package com.github.maitmus.pcgspring.card.v1.repository;

import com.github.maitmus.pcgspring.card.v1.entity.Card;
import com.github.maitmus.pcgspring.common.constant.EntityStatus;
import com.github.maitmus.pcgspring.user.v1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByIsRepresentativeAndUserAndStatus(Boolean isRepresentative, User user, EntityStatus status);

    List<Card> findByUserAndStatusOrderByIsRepresentativeDesc(User user, EntityStatus status);

    List<Card> findByUserAndStatus(User user, EntityStatus entityStatus);

    Optional<Card> findOneByUserAndStatusAndIsRepresentativeIsTrue(User user, EntityStatus status);

    Optional<Card> findByIdAndUserAndStatus(Long id, User user, EntityStatus entityStatus);
}
