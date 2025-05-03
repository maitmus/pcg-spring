package com.github.maitmus.pcgspring.park.v1.dto;

import com.github.maitmus.pcgspring.park.v1.entity.Park;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParkDetail {
    private String name;
    private String phone;
    private String address;
    private Integer disabilitySpace;
    private Double lat;
    private Double lon;

    public ParkDetail(Park park) {
        this.name = park.getName();
        this.phone = park.getPhone();
        this.address = park.getAddress();
        this.disabilitySpace = park.getDisabilitySpace();
        this.lat = park.getLat();
        this.lon = park.getLon();
    }
}
