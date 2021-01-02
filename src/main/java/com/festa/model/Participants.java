package com.festa.model;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Value
@Builder
public class Participants {

    @NotNull
    int eventNo;

    @NotNull
    int userNo;

    String userName;

    String cityName;

    String districtName;

    String streetCode;

    String streetName;

    String detail;

    Date applyDate;

    Date cancelDate;

    Date participateDate;

    public Participants toEntityForAddress() {

        return Participants.builder()
                .userNo(this.userNo)
                .eventNo(this.eventNo)
                .cityName(this.cityName)
                .districtName(this.districtName)
                .streetCode(this.streetCode)
                .streetName(this.streetName)
                .detail(this.detail)
                .build();
    }

}
