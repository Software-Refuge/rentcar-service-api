package uz.carapp.rentcarapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import uz.carapp.rentcarapp.domain.*;
import uz.carapp.rentcarapp.domain.enumeration.MerchantRoleEnum;
import uz.carapp.rentcarapp.repository.*;
import uz.carapp.rentcarapp.rest.errors.BadRequestCustomException;
import uz.carapp.rentcarapp.security.UserDetailsServiceImpl;
import uz.carapp.rentcarapp.service.MerchantService;
import uz.carapp.rentcarapp.service.dto.*;
import uz.carapp.rentcarapp.service.mapper.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service Implementation for managing {@link Merchant}.
 */
@Service
@Transactional
public class MerchantServiceImpl implements MerchantService {

    private static final Logger LOG = LoggerFactory.getLogger(MerchantServiceImpl.class);

    private final MerchantRepository merchantRepository;

    private final MerchantMapper merchantMapper;
    private final UserRepository userRepository;
    private final MerchantRoleRepository merchantRoleRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final MerchantRoleMapper merchantRoleMapper;

    private final AttachmentRepository attachmentRepository;
    private final AttachmentService attachmentService;
    private final MerchantDocumentRepository merchantDocumentRepository;

    private final MerchantDocumentMapper merchantDocumentMapper;

    private final AttachmentMapper attachmentMapper;

    private final DocumentMapper documentMapper;
    private final DocAttachmentRepository docAttachmentRepository;

    @Value("${minio.external}")
    private String BASE_URL;

    public MerchantServiceImpl(
            MerchantRepository merchantRepository,
            MerchantMapper merchantMapper,
            UserRepository userRepository, MerchantRoleRepository merchantRoleRepository, UserDetailsServiceImpl userDetailsService, MerchantRoleMapper merchantRoleMapper, AttachmentRepository attachmentRepository, AttachmentService attachmentService, MerchantDocumentRepository merchantDocumentRepository, MerchantDocumentMapper merchantDocumentMapper, AttachmentMapper attachmentMapper, DocumentMapper documentMapper, DocAttachmentRepository docAttachmentRepository) {
        this.merchantRepository = merchantRepository;
        this.merchantMapper = merchantMapper;
        this.userRepository = userRepository;
        this.merchantRoleRepository = merchantRoleRepository;
        this.userDetailsService = userDetailsService;
        this.merchantRoleMapper = merchantRoleMapper;
        this.attachmentRepository = attachmentRepository;
        this.attachmentService = attachmentService;
        this.merchantDocumentRepository = merchantDocumentRepository;
        this.merchantDocumentMapper = merchantDocumentMapper;
        this.attachmentMapper = attachmentMapper;
        this.documentMapper = documentMapper;
        this.docAttachmentRepository = docAttachmentRepository;
    }

    @Override
    public MerchantDTO save(MerchantSaveDTO merchantSaveDTO) {
        LOG.info("Request to save Merchant : {}", merchantSaveDTO);

        //check user already attached to merchant
        if(userRepository.checkUserAssignedToMerchant(merchantSaveDTO.getUserId())>0) {
            throw new BadRequestCustomException("User already assigned other merchant","Merchant","M001");
        }

        //check user has USER role
        if(userRepository.checkHasUserRole(merchantSaveDTO.getUserId())==0) {
            throw new BadRequestCustomException("User has no 'USER' role","Merchant","M002");
        }

        Merchant merchant = merchantMapper.toEntity(merchantSaveDTO);
        merchant = merchantRepository.save(merchant);

        //assign OWNER ROLE to user
        MerchantRoleDTO merchantRoleDTO = new MerchantRoleDTO();
        merchantRoleDTO.setMerchant(merchantMapper.toDto(merchant));
        merchantRoleDTO.setUser(new UserDTO(merchantSaveDTO.getUserId()));
        merchantRoleDTO.setMerchantRoleType(MerchantRoleEnum.ROLE_OWNER);
        merchantRoleRepository.save(merchantRoleMapper.toEntity(merchantRoleDTO));

        return merchantMapper.toDto(merchant);
    }

    @Override
    public Optional<MerchantDTO> partialUpdate(MerchantEditDTO merchantEditDTO) {
        LOG.info("Request to partially update Merchant : {}", merchantEditDTO);

        return merchantRepository
            .findById(merchantEditDTO.getId())
            .map(existingMerchant -> {
                merchantMapper.partialUpdate(existingMerchant, merchantEditDTO);

                return existingMerchant;
            })
            .map(merchantRepository::save)
            .map(merchantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MerchantDTO> findAll(String search, Pageable pageable) {
        LOG.info("Request to get all Merchants");
        if(StringUtils.hasText(search))
            search = search.toLowerCase();
        Page<Merchant> page = merchantRepository.findAll(search, pageable);

        return page.map(merchantMapper::toDto)
                .map(merchantDTO -> {
                    AttachmentDTO attachment = merchantDTO.getAttachment();
                    if(attachment != null) {
                        attachment.setPath(BASE_URL + File.separator + attachment.getPath());
                        merchantDTO.setAttachment(attachment);
                    }
                    return merchantDTO;
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MerchantDTO> findOne(Long id) {
        LOG.info("Request to get Merchant : {}", id);

        List<MerchantDocument> merchantDocuments = merchantDocumentRepository.findAllDocumentsByMerchantId(id);

        List<Long> docIds = merchantDocuments.stream()
                .map(merchantDocument -> merchantDocument.getMerchant().getId())
                .toList();

        Map<Long,List<Attachment>> docAttachMap = docAttachmentRepository.getAttachmentsByDocIds(docIds)
                        .stream()
                                .collect(Collectors.groupingBy(docAttachment -> docAttachment.getDocument().getId(),
                                        Collectors.mapping(DocAttachment::getAttachment,Collectors.toList())));


        //get all attachments by docIds
        List<DocumentDTO> list = merchantDocuments.stream()
                .map(merchantDocument -> {
                    Document document = merchantDocument.getDocument();
                    DocumentDTO dto = documentMapper.toDto(document);
                    dto.setAttachments(attachmentMapper.toDto(docAttachMap.get(document.getId())));
                    return dto;
                }).toList();

        return merchantRepository.findById(id).map(merchantMapper::toDto)
            .map(merchantDTO -> {
                if(merchantDTO.getAttachment()!=null) {
                    AttachmentDTO attachmentDTO = merchantDTO.getAttachment();
                    attachmentDTO.setPath(BASE_URL+File.separator+attachmentDTO.getPath());
                    merchantDTO.setAttachment(attachmentDTO);
                }
                merchantDTO.setDocuments(list);
                return merchantDTO;
            });
    }

    @Override
    public void delete(Long id) {
        LOG.info("Request to delete Merchant : {}", id);
        merchantRepository.deleteById(id);
    }

    @Override
    public void uploadImage(Long merchantId, Long attachmentId) {
        LOG.info("Request to upload image by merchantId:{} and attachmentId:{}",merchantId,attachmentId);

        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new BadRequestCustomException("Merchant not found","",""));

        if(merchant.getAttachment()!=null) {
            Long oldAttachmentId = merchant.getAttachment().getId();
            merchant.setAttachment(null);
            merchantRepository.save(merchant);
            attachmentService.delete(oldAttachmentId);
        }

        // Yangi attachmentni o‘rnatish
        Attachment newAttachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new BadRequestCustomException("Attachment not found","",""));

        merchant.setAttachment(newAttachment);
        merchantRepository.save(merchant);
    }
}
