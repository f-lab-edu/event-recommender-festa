package com.festa.common.commonService;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface ImageUploader {

    String uploadImage (MultipartFile multipartFile) throws IOException;

    void deleteFile(String key);

}
