package com.festa.common;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.festa.common.commonService.ImageUploader;
import com.festa.common.util.ConvertDataType;
import com.festa.exception.ImageUploadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;

@Log4j2
@Component
@RequiredArgsConstructor
public class S3ImageUploader implements ImageUploader {

    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Value("${aws.s3.accessKey}")
    private String accessKey;

    @Value("${aws.s3.secretKey}")
    private String secretKey;

    @Value("${aws.s3.region}")
    private String region;

    /**
     * AmazonS3 초기화
     * @PostConstruct에 의해 어플리케이션 시작 시 한번만 실행
     */
    @PostConstruct
    private void s3Client() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

        amazonS3 = AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

    /**
     * S3에 파일 업로드
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @Override
    public String uploadImage(MultipartFile multipartFile) {
        String newFileName = changeFileName(multipartFile);

        try {
            String fileType = checkFileType(multipartFile);

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(fileType);

            amazonS3.putObject(new PutObjectRequest(bucket, newFileName, multipartFile.getInputStream(), objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException exception) {
            throw new ImageUploadException("이미지 업로드에 실패하였습니다.");
        }

        return amazonS3.getUrl(bucket, newFileName).toString();
    }

    /**
     * S3에서 파일 삭제
     * @param fileName
     */
    @Override
    public void deleteFile(String fileName) {
        amazonS3.deleteObject(bucket, fileName);
    }

    /**
     * 파일 타입 확인
     * Files.probeContentType()은 확장자를 이용하여 타입을 확인하기 때문에 확장자가 없으면 null을 리턴
     * 대신 Apache Tika는 메타데이터를 이용하여 타입을 확인하기 때문에 확장자가 없어도 정확한 타입을 확인할 수 있음
     * 파일 타입이 사진이 아닐 경우 IllegaArgumentException 발생 후 중단
     *
     * @param multipartFile
     * @return fileType
     */
    public String checkFileType(MultipartFile multipartFile) throws IOException {
        String fileType = new Tika().detect(multipartFile.getInputStream());

        if (MediaType.IMAGE_JPEG_VALUE.equals(fileType) || MediaType.IMAGE_PNG_VALUE.equals(fileType) || MediaType.IMAGE_GIF_VALUE.equals(fileType)) {
            throw new IllegalArgumentException("사진 파일이 아닙니다.");
        }

        return fileType;
    }

    /**
     * '업로드날짜_파일이름'로 파일 이름 변경
     * @param multipartFile
     * @return
     */
    public String changeFileName(MultipartFile multipartFile) {
        StringBuffer newFileName = new StringBuffer();

        newFileName.append(ConvertDataType.dateFormatter(LocalDate.now()));
        newFileName.append("_");
        newFileName.append(multipartFile.getOriginalFilename());

        return newFileName.toString();
    }
}
