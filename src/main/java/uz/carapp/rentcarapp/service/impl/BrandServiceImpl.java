package uz.carapp.rentcarapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.carapp.rentcarapp.domain.Attachment;
import uz.carapp.rentcarapp.domain.Brand;
import uz.carapp.rentcarapp.repository.AttachmentRepository;
import uz.carapp.rentcarapp.repository.BrandRepository;
import uz.carapp.rentcarapp.rest.errors.BadRequestCustomException;
import uz.carapp.rentcarapp.service.BrandService;
import uz.carapp.rentcarapp.service.dto.AttachmentDTO;
import uz.carapp.rentcarapp.service.dto.BrandDTO;
import uz.carapp.rentcarapp.service.dto.BrandEditDTO;
import uz.carapp.rentcarapp.service.dto.BrandSaveDTO;
import uz.carapp.rentcarapp.service.mapper.BrandMapper;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Brand}.
 */
@Service
@Transactional
public class BrandServiceImpl implements BrandService {

    private static final Logger LOG = LoggerFactory.getLogger(BrandServiceImpl.class);

    private final BrandRepository brandRepository;

    private final BrandMapper brandMapper;
    private final AttachmentRepository attachmentRepository;

    @Value("${minio.external}")
    private String BASE_URL;


    public BrandServiceImpl(BrandRepository brandRepository, BrandMapper brandMapper, AttachmentRepository attachmentRepository) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    public BrandDTO save(BrandSaveDTO brandSaveDTO) {
        LOG.info("Request to save Brand : {}", brandSaveDTO);
        Brand brand = brandMapper.toEntity(brandSaveDTO);
        brand = brandRepository.save(brand);
        return brandMapper.toDto(brand);
    }

    @Override
    public BrandDTO update(BrandDTO brandDTO) {
        LOG.debug("Request to update Brand : {}", brandDTO);
        Brand brand = brandMapper.toEntity(brandDTO);
        brand = brandRepository.save(brand);
        return brandMapper.toDto(brand);
    }

    @Override
    public Optional<BrandDTO> partialUpdate(BrandEditDTO brandEditDTO) {
        LOG.info("Request to partially update Brand : {}", brandEditDTO);

        return brandRepository
            .findById(brandEditDTO.getId())
            .map(existingBrand -> {
                brandMapper.partialUpdate(existingBrand, brandEditDTO);

                return existingBrand;
            })
            .map(brandRepository::save)
            .map(brandMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BrandDTO> findAll(Pageable pageable) {
        LOG.info("Request to get all Brands");
        Page<Brand> page = brandRepository.findAll(pageable);
        List<BrandDTO> list = page.stream().map(brandMapper::toDto)
                .map(brandDTO -> {
                    AttachmentDTO attachment = brandDTO.getAttachment();
                    if (attachment != null) {
                        attachment.setPath(BASE_URL + File.separator + attachment.getPath());
                        brandDTO.setAttachment(attachment);
                    }
                    return brandDTO;
                }).toList();

        return new PageImpl<>(list,pageable,page.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BrandDTO> findOne(Long id) {
        LOG.info("Request to get Brand : {}", id);
        return brandRepository.findById(id).map(brandMapper::toDto)
                .map(brandDTO -> {
                    if(brandDTO.getAttachment()!=null) {
                        AttachmentDTO attachment = brandDTO.getAttachment();
                        attachment.setPath(BASE_URL+ File.separator +attachment.getPath());
                        brandDTO.setAttachment(attachment);
                    }
                    return brandDTO;
                });
    }

    @Override
    public void delete(Long id) {
        LOG.info("Request to delete Brand : {}", id);
        brandRepository.deleteById(id);
    }

    @Override
    public void uploadImage(Long brandId, Long attachmentId) {
        LOG.info("Request to upload image by brandId:{} and attachmentId:{}",brandId,attachmentId);

        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new BadRequestCustomException("Brand not found","",""));

        if(brand.getAttachment()!=null) {
            Long oldAttachmentId = brand.getAttachment().getId();
            brand.setAttachment(null);
            brandRepository.save(brand);
            attachmentRepository.deleteById(oldAttachmentId);
        }

        // Yangi attachmentni o‘rnatish
        Attachment newAttachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new BadRequestCustomException("Attachment not found","",""));

        brand.setAttachment(newAttachment);
        brandRepository.save(brand);
    }
}
