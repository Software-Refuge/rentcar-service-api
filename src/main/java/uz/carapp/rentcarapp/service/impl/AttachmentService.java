package uz.carapp.rentcarapp.service.impl;

import io.minio.GenericResponse;
import io.minio.errors.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.carapp.rentcarapp.domain.Attachment;
import uz.carapp.rentcarapp.domain.enumeration.AttachmentTypeEnum;
import uz.carapp.rentcarapp.repository.AttachmentRepository;
import uz.carapp.rentcarapp.service.MinioService;
import uz.carapp.rentcarapp.service.dto.AttachmentDTO;
import uz.carapp.rentcarapp.service.mapper.AttachmentMapper;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class AttachmentService implements uz.carapp.rentcarapp.service.AttachmentService {

    private final Logger log = LoggerFactory.getLogger(AttachmentService.class);

    private final MinioService minioService;

    private final AttachmentMapper attachmentMapper;

    private final AttachmentRepository attachmentRepository;

    @Value("${minio.bucketDefaultName}")
    private String BUCKET_NAME;

    @Value("${minio.external}")
    private String BASE_URL;

    public AttachmentService(MinioService minioService, AttachmentMapper attachmentMapper, AttachmentRepository attachmentRepository) {
        this.minioService = minioService;
        this.attachmentMapper = attachmentMapper;
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    public AttachmentDTO saveFile(MultipartFile file, AttachmentTypeEnum attachmentTypeEnum) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info("Request to save file by attachmentType:{}", attachmentTypeEnum);

        //1. save file to minio server
        GenericResponse genericResponse = minioService.uploadFile(file);
        String objectName = genericResponse.object();

        //2. save Attachment data to DB
        AttachmentDTO attachmentDTO = fillFromFileAttachmentDTO(file);
        attachmentDTO.setFileName(objectName);
        attachmentDTO.setPath(getPath()+objectName);

        return save(attachmentDTO);
    }

    private AttachmentDTO save(AttachmentDTO attachmentDTO) {
        Attachment attachment = attachmentMapper.toEntity(attachmentDTO);
        attachment = attachmentRepository.save(attachment);
        return attachmentMapper.toDto(attachment);
    }

    public AttachmentDTO fillFromFileAttachmentDTO(MultipartFile file) {
        log.info("Request to make General AttachmentDTO by fileName: {}", file.getOriginalFilename());

        AttachmentDTO attachment = new AttachmentDTO();
        String orgFileName = file.getOriginalFilename();
        // Start to fill attachment model.
        attachment.setOriginalFileName(orgFileName);
        attachment.setExt(getFileExtension(orgFileName));
        attachment.setFileSize(file.getSize());
        return attachment;
    }

    public AttachmentDTO findById(Long attachmentId) {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(attachmentId);
        if(optionalAttachment.isPresent()) {
            AttachmentDTO attachmentDTO = attachmentMapper.toDto(optionalAttachment.get());
            String path = getFilePath() + attachmentDTO.getFileName();
            attachmentDTO.setPath(path);
            return attachmentDTO;
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        log.info("Request to delete Attachment :{}",id);
        attachmentRepository.findById(id).ifPresent(attachment -> minioService.delete(attachment.getFileName()));
        attachmentRepository.deleteById(id);
    }

    private String getFilePath() {
        return BASE_URL + "/" + BUCKET_NAME + "/";
    }

    private String getPath() {
        return BUCKET_NAME + "/";
    }

    private String getFileExtension(String fileName) {
        String extension = "";
        int index = fileName.lastIndexOf('.');
        if (index >= 0) {
            extension = fileName.substring(index + 1);
        }
        return extension;
    }
}
