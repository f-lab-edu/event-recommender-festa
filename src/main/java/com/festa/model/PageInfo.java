package com.festa.model;

import lombok.Value;

@Value
public class PageInfo {

    long cursorUserNo;

    int size;

    public static PageInfo paging(long cursorUserNo, int size) {

        return new PageInfo(cursorUserNo, size);
    }
}
