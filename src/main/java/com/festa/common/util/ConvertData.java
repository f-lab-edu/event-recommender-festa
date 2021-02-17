package com.festa.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertData {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    public static String getTodayDate() {
        return dateFormat.format(new Date());
    }

    public static String longToString(long target) {
        return String.valueOf(target);
    }
}
