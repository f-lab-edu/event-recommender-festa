package com.festa.common

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.festa.common.commonService.ImageUploaderKT
import com.festa.common.util.ConvertDataTypeKT
import com.festa.exception.ImageUploadException
import lombok.extern.log4j.Log4j2
import org.apache.tika.Tika
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.time.LocalDate
import javax.annotation.PostConstruct

@Log4j2
@Component
class S3ImageUploaderKT : ImageUploaderKT{

    private lateinit var amazonS3: AmazonS3

    @Value("\${aws.s3.bucket}")
    private lateinit var bucket: String

    @Value("\${aws.s3.accessKey}")
    private lateinit var accessKey: String

    @Value("\${aws.s3.secretKey}")
    private lateinit var secretKey: String

    @Value("\${aws.s3.region}")
    private lateinit var region: String

    @PostConstruct
    private fun s3Client() {
        val awsCredentials = BasicAWSCredentials(accessKey, secretKey)

        amazonS3 = AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(AWSStaticCredentialsProvider(awsCredentials))
                .build()
    }

    override fun uploadImage(multipartFile: MultipartFile): String {
        val newFileName = changeFileName(multipartFile)

        try {
            val fileType = checkFileType(multipartFile)

            var objectMetadata = ObjectMetadata()
            objectMetadata.contentType = fileType

            amazonS3.putObject(PutObjectRequest(bucket, newFileName, multipartFile.inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead))
        } catch (e: IOException) {
            throw ImageUploadException("이미지 업로드에 실패하였습니다.")
        }
        return amazonS3.getUrl(bucket, newFileName).toString()
    }

    override fun deleteFile(fileName: String) {
        amazonS3.deleteObject(bucket, fileName)
    }

    @Throws(IOException::class)
    fun checkFileType(multipartFile: MultipartFile): String {
        val fileType = Tika().detect(multipartFile.inputStream)

        // require() 인자로 받은 표현식이 거짓일 경우 IllegalArgumentException을 발생시킨다.
        require(MediaType.IMAGE_JPEG_VALUE == fileType ||
                MediaType.IMAGE_PNG_VALUE == fileType ||
                MediaType.IMAGE_GIF_VALUE == fileType) {
            "사진 파일이 아닙니다."
        }

        return fileType
    }

    fun changeFileName(multipartFile: MultipartFile): String {
        var newFileName = StringBuffer()

        newFileName.append(ConvertDataTypeKT.dateFormatter(LocalDate.now()))
        newFileName.append("_")
        newFileName.append(multipartFile.originalFilename)

        return newFileName.toString()
    }
}