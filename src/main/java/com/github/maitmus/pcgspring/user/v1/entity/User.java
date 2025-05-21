package com.github.maitmus.pcgspring.user.v1.entity;

import com.github.maitmus.pcgspring.card.v1.entity.Card;
import com.github.maitmus.pcgspring.common.constant.EntityStatus;
import com.github.maitmus.pcgspring.common.constant.Role;
import com.github.maitmus.pcgspring.common.entity.BaseEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
public class User extends BaseEntity {
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "userId"))
    @Enumerated(EnumType.STRING)
    private final List<Role> roles = new ArrayList<>();
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
    @OneToMany
    private List<Card> cards;

    public User(String name, String nickname, String email, String username, String password, LocalDate birth) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.birth = birth;
        this.roles.add(Role.USER);
        this.status = EntityStatus.ACTIVE;
    }

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
