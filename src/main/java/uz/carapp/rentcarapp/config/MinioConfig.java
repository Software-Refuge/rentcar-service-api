package uz.carapp.rentcarapp.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class MinioConfig {

    /**
     * Конечная точка - это URL, доменное имя, IPv4 или IPv6 -адрес
     */
    @Value("${minio.endpoint}")
    private String endpoint;

    /**
     * Номер порта TCP/IP
     */
    @Value("${minio.port}")
    private int port;

    /**
     * AccessKey похож на идентификатор пользователя, используемый для уникальной идентификации вашей учетной записи
     */
    @Value("${minio.accessKey}")
    private String accessKey;

    /**
     * SecretKey - это пароль вашей учетной записи
     */
    @Value("${minio.secretKey}")
    private String secretKey;

    /**
     * Если это правда, это HTTPS вместо HTTP, а значение по умолчанию верно
     */
    @Value("${minio.secure}")
    private Boolean secure;

    /**
     * Имя ведра по умолчанию
     */
    @Value("${minio.bucketDefaultName}")
    private String bucketDefaultName;

    @Bean
    public MinioClient getMinioClient() {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(endpoint , port , secure)
                .credentials(accessKey, secretKey)
                .build();
        return minioClient;
    }
}
