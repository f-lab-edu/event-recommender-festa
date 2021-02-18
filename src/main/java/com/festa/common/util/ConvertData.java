package com.festa.common.util;

import org.joda.time.LocalDate;

public class ConvertData {

    public static LocalDate getTodayDate() {
        return LocalDate.now();
    }

    public static String longToString(long target) {
        return String.valueOf(target);
    }
}
