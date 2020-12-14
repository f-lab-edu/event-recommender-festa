package com.festa.model;

import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Value
public class Participants {

    @NotNull
    int eventNo;

    @NotNull
    int userNo;

    Date applyDate;

    Date cancelDate;

    Date participateDate;

}
