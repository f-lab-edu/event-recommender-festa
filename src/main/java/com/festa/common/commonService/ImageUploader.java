package com.festa.common.commonService;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ImageUploader {

    String uploadImage (MultipartFile multipartFile);

    void deleteFile(String key);

}
