package com.festa.model;

import lombok.Value;

import java.util.Date;

@Value
public class Participants {

    int eventNo;

    int userNo;

    Date applyDate;

    Date cancelDate;

    Date participateDate;

}
