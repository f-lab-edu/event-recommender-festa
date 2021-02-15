package com.festa.common.commonService;

import org.springframework.stereotype.Service;

@Service
public interface ConvertDataType {

    String dataFormat();

    String longToString(long target);
}
