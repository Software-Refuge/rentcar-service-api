package uz.carapp.rentcarapp.service;

import io.minio.*;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@Service
public class MinioService {

    private final Logger log = LoggerFactory.getLogger(MinioService.class);
    private final MinioClient minioClient;
    @Value("${minio.bucketDefaultName}")
    private String BUCKET_NAME;


    private void initBucket() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info("Make 'bucketDefaultName' bucket if not exist");

        // Make 'BUCKET_NAME' bucket if not exist.
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME).build())) {
            // Make a new bucket called 'BUCKET_NAME'.
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(BUCKET_NAME).build());
        }
    }


    public GenericResponse uploadFile(MultipartFile multipartFile) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info("Request to upload one file by MinioClient");

        // Make 'bucketDefaultName' bucket if not exist.
        initBucket();
        // Make builder file for put Object into Minio.
        PutObjectArgs.Builder putObjectArgs = makePutObjectArgs(multipartFile);
        return finalUploadImage(putObjectArgs);
    }


    public PutObjectArgs.Builder makePutObjectArgs(MultipartFile multipartFile) throws IOException {
        log.info("Request to make PutObjectArgs builder");

        // Get encoded + decoded original file name
        String originalFileName = FileUtils.encodeFileName(multipartFile.getOriginalFilename());
        String generateUniqueName = FileUtils.generateUniqueName(originalFileName);

        return PutObjectArgs.builder()
                .bucket(BUCKET_NAME)
                .object(generateUniqueName)
                .contentType(multipartFile.getContentType())
                .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1);
    }


    private GenericResponse finalUploadImage(PutObjectArgs.Builder putObject) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info("Image success saved. To bucket: " + BUCKET_NAME);
        return minioClient.putObject(putObject.build());
    }

    /**
     * Get inputStream from minio by bucketName and fileName.
     *
     * @param fileName type of String.
     * @return the {@link InputStream}
     */
    @SneakyThrows
    public InputStream getObjectByBucketNameAndFileName(String fileName) {
        System.out.println(BUCKET_NAME + " " + fileName);
        return minioClient.getObject(GetObjectArgs.builder()
                .bucket(BUCKET_NAME)
                .object(fileName)
                .build());
    }

    @SneakyThrows
    public void delete(String objectName) {
        minioClient.removeObject(
                RemoveObjectArgs.builder().bucket(BUCKET_NAME).object(objectName).build());
    }


}
