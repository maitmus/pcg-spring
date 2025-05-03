package com.github.maitmus.pcgspring.auth.v1.entity;

import com.github.maitmus.pcgspring.auth.v1.event.EmailTokenCreatedEventListener;
import com.github.maitmus.pcgspring.common.entity.BaseEntity;
import com.github.maitmus.pcgspring.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "email_tokens")
@EntityListeners(EmailTokenCreatedEventListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EmailToken extends BaseEntity {
    @Column(nullable = false, length = 50)
    private String token;

    @OneToOne(optional = false)
    private User user;
}
