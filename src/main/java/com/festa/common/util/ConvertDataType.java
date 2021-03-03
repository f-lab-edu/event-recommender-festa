package com.festa.common.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConvertDataType {

    public static final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String dateFormatter(LocalDate todayDate) {
        return todayDate.format(format);
    }

    public static String longToString(long target) {
        return String.valueOf(target);
    }
}
