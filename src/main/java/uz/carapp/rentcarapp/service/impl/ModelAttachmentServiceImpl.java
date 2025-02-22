package uz.carapp.rentcarapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.carapp.rentcarapp.domain.ModelAttachment;
import uz.carapp.rentcarapp.repository.ModelAttachmentRepository;
import uz.carapp.rentcarapp.service.ModelAttachmentService;
import uz.carapp.rentcarapp.service.dto.ModelAttachmentDTO;
import uz.carapp.rentcarapp.service.dto.ModelAttachmentSaveDTO;
import uz.carapp.rentcarapp.service.mapper.ModelAttachmentMapper;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ModelAttachment}.
 */
@Service
@Transactional
public class ModelAttachmentServiceImpl implements ModelAttachmentService {

    private static final Logger LOG = LoggerFactory.getLogger(ModelAttachmentServiceImpl.class);

    private final ModelAttachmentRepository modelAttachmentRepository;

    private final ModelAttachmentMapper modelAttachmentMapper;

    public ModelAttachmentServiceImpl(
        ModelAttachmentRepository modelAttachmentRepository,
        ModelAttachmentMapper modelAttachmentMapper
    ) {
        this.modelAttachmentRepository = modelAttachmentRepository;
        this.modelAttachmentMapper = modelAttachmentMapper;
    }

    @Override
    public ModelAttachmentDTO save(ModelAttachmentSaveDTO modelAttachmentSaveDTO) {
        LOG.info("Request to save ModelAttachment : {}", modelAttachmentSaveDTO);
        ModelAttachment modelAttachment = modelAttachmentMapper.toEntity(modelAttachmentSaveDTO);
        modelAttachment = modelAttachmentRepository.save(modelAttachment);
        return modelAttachmentMapper.toDto(modelAttachment);
    }

    @Override
    public ModelAttachmentDTO update(ModelAttachmentDTO modelAttachmentDTO) {
        LOG.debug("Request to update ModelAttachment : {}", modelAttachmentDTO);
        ModelAttachment modelAttachment = modelAttachmentMapper.toEntity(modelAttachmentDTO);
        modelAttachment = modelAttachmentRepository.save(modelAttachment);
        return modelAttachmentMapper.toDto(modelAttachment);
    }

    @Override
    public Optional<ModelAttachmentDTO> partialUpdate(ModelAttachmentDTO modelAttachmentDTO) {
        LOG.debug("Request to partially update ModelAttachment : {}", modelAttachmentDTO);

        return modelAttachmentRepository
            .findById(modelAttachmentDTO.getId())
            .map(existingModelAttachment -> {
                modelAttachmentMapper.partialUpdate(existingModelAttachment, modelAttachmentDTO);

                return existingModelAttachment;
            })
            .map(modelAttachmentRepository::save)
            .map(modelAttachmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ModelAttachmentDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all ModelAttachments");
        return modelAttachmentRepository.findAll(pageable).map(modelAttachmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ModelAttachmentDTO> findOne(Long id) {
        LOG.debug("Request to get ModelAttachment : {}", id);
        return modelAttachmentRepository.findById(id).map(modelAttachmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.info("Request to delete ModelAttachment : {}", id);
        modelAttachmentRepository.deleteById(id);
    }

    @Override
    public void setMainPhoto(Long modelId, Long attachmentId) {
        LOG.info("Request to set main photo for ModelAttachment : {} and attachmentId:{}", modelId,attachmentId);

        modelAttachmentRepository.removeOldMainPhoto(modelId);
        modelAttachmentRepository.setMainPhoto(modelId,attachmentId);
    }
}
