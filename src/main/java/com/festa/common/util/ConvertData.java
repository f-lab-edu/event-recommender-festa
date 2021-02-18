package com.festa.common.util;

import org.joda.time.LocalDate;

public class ConvertData {

    public static String getTodayDate() {
        return String.valueOf(LocalDate.now());
    }

    public static String longToString(long target) {
        return String.valueOf(target);
    }
}
