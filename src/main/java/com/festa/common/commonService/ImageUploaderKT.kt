package com.festa.common.commonService

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
interface ImageUploaderKT {

    fun uploadImage(multipartFile: MultipartFile): String

    fun deleteFile(key: String)
}