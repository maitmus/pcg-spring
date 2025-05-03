package com.github.maitmus.pcgspring.car.v1.entity;

import com.github.maitmus.pcgspring.common.entity.BaseEntity;
import com.github.maitmus.pcgspring.user.v1.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "cars")
@NoArgsConstructor
public class Car extends BaseEntity {
    @Column(nullable = false)
    private String carNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Car(String carNumber, User user) {
        this.carNumber = carNumber;
        this.user = user;
    }
}
