package com.github.maitmus.pcgspring.user.entity;

import com.github.maitmus.pcgspring.card.entity.Card;
import com.github.maitmus.pcgspring.common.constant.Role;
import com.github.maitmus.pcgspring.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    @Column(length = 20)
    private String name;

    @Column(length = 20, unique = true, nullable = false)
    private String nickname;

    @Column(length = 100, unique = true, nullable = false)
    private String email;

    @Column(length = 20, unique = true, nullable = false)
    private String username;

    @Column(length = 200, nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "userId"))
    @Enumerated(EnumType.STRING)
    private List<Role> roles = new ArrayList<>();

    @OneToOne
    private Card card;

    public void setHashedPassword(String hashedPassword) {
        this.password = hashedPassword;
    }

    public void update(String nickname, String email) {
        if (nickname != null && !nickname.isEmpty()) {
            this.nickname = nickname;
        }
        if (email != null && !email.isEmpty()) {
            this.email = email;
        }
    }
}
