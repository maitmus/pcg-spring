package com.github.maitmus.pcgspring.user.v1.entity;

import com.github.maitmus.pcgspring.card.v1.entity.Card;
import com.github.maitmus.pcgspring.common.constant.Role;
import com.github.maitmus.pcgspring.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
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

    @Column
    private LocalDate birth;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "userId"))
    @Enumerated(EnumType.STRING)
    private List<Role> roles = new ArrayList<>();

    @OneToMany
    private List<Card> cards;

    public void setHashedPassword(String hashedPassword) {
        this.password = hashedPassword;
    }

    public void update(String nickname, String email) {
        if (StringUtils.hasText(nickname)) {
            this.nickname = nickname;
        }
        if (StringUtils.hasText(email)) {
            this.email = email;
        }
    }
}
