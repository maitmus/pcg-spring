package com.github.maitmus.pcgspring.auth.v1.entity;

import com.github.maitmus.pcgspring.common.entity.BaseEntity;
import com.github.maitmus.pcgspring.user.v1.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "email_tokens")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EmailToken extends BaseEntity {
    @Column(nullable = false, length = 100)
    private String token;

    @OneToOne(optional = false)
    private User user;
}
