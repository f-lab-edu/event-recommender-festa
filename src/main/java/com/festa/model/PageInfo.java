package com.festa.model;

import lombok.Value;

@Value
public class PageInfo {

    Integer noPageLoad;

    int size;

    public static PageInfo paging(Integer noPageLoad, int size) {

        return new PageInfo(noPageLoad, size);
    }
}
