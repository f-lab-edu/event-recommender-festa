package com.festa.model;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Pattern;

@Value
@Builder
public class MemberInfo {

    long userNo;

    String userName;

    @Pattern(regexp = "(^02.{0}|^01.{1}|[0-9]{3})([0-9]{4})([0-9]{4})")
    int phoneNo;

    String cityName;

    String districtName;

    String streetCode;

    String streetName;

    String userModifyInfo;

    public MemberInfo toEntityForInfo() {

        return MemberInfo.builder()
                .userNo(this.userNo)
                .userName(this.userName)
                .phoneNo(this.phoneNo)
                .build();
    }

    public MemberInfo toEntityForAddress() {

        return MemberInfo.builder()
                .userNo(this.userNo)
                .cityName(this.cityName)
                .districtName(this.districtName)
                .streetCode(this.streetCode)
                .streetName(this.streetName)
                .build();
    }
}
