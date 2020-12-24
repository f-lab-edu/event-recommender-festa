package com.festa.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Address {

    int eventNo;

    long userNo;

    String cityName;

    String districtName;

    String streetCode;

    String streetName;

    String detail;

    public Address toEntityForMember() {

        return Address.builder()
                .userNo(this.userNo)
                .cityName(this.cityName)
                .districtName(this.districtName)
                .streetCode(this.streetCode)
                .streetName(this.streetName)
                .build();
    }

    public Address toEntityForEvent() {

        return Address.builder()
                .eventNo(this.eventNo)
                .cityName(this.cityName)
                .districtName(this.districtName)
                .streetCode(this.streetCode)
                .detail(this.detail)
                .streetName(this.streetName)
                .build();
    }
}
