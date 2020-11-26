package com.festa.model;

import lombok.Value;

@Value
public class PageInfo {

    Integer noPageLoad;

    int size = 20;

    public static PageInfo paging(Integer noPageLoad) {

        return new PageInfo(noPageLoad);
    }
}
