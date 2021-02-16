package com.festa.common;

import com.festa.common.commonService.ConvertDataType;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ConvertData implements ConvertDataType {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public String dataFormat() {
        return dateFormat.format(new Date());
    }

    @Override
    public String longToString(long target) {
        return String.valueOf(target);
    }
}
