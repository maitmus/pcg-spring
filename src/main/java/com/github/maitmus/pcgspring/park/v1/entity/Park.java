package com.github.maitmus.pcgspring.park.v1.entity;

import com.github.maitmus.pcgspring.common.constant.EntityStatus;
import com.github.maitmus.pcgspring.common.entity.BaseEntity;
import com.github.maitmus.pcgspring.user.v1.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "parks")
@Getter
@NoArgsConstructor
public class Park extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column
    private String phone;

    @Column
    private String address;

    @Column
    private Integer disabilitySpace;

    @Column(unique = true)
    private String manageCode;

    @Column
    private Double lat;

    @Column
    private Double lon;

    @Column
    private String ip;

    @OneToOne
    private User owner;

    public Park(String name, String phone, String address, Integer disabilitySpace, String manageCode, Double lat,
                Double lon, String ip, User owner) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.disabilitySpace = disabilitySpace;
        this.manageCode = manageCode;
        this.lat = lat;
        this.lon = lon;
        this.ip = ip;
        this.owner = owner;
        this.status = EntityStatus.INACTIVE;
    }
}
