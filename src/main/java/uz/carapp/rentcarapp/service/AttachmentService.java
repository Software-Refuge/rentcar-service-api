package uz.carapp.rentcarapp.service;

import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;
import uz.carapp.rentcarapp.domain.enumeration.AttachmentTypeEnum;
import uz.carapp.rentcarapp.service.dto.AttachmentDTO;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface AttachmentService {

    AttachmentDTO saveFile(MultipartFile file, AttachmentTypeEnum attachmentTypeEnum) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;
}
