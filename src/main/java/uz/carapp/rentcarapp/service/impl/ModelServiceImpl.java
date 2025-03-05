package uz.carapp.rentcarapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.carapp.rentcarapp.domain.Attachment;
import uz.carapp.rentcarapp.domain.Model;
import uz.carapp.rentcarapp.domain.ModelAttachment;
import uz.carapp.rentcarapp.repository.ModelAttachmentRepository;
import uz.carapp.rentcarapp.repository.ModelRepository;
import uz.carapp.rentcarapp.service.ModelService;
import uz.carapp.rentcarapp.service.dto.*;
import uz.carapp.rentcarapp.service.mapper.AttachmentMapper;
import uz.carapp.rentcarapp.service.mapper.ModelAttachmentMapper;
import uz.carapp.rentcarapp.service.mapper.ModelMapper;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Model}.
 */
@Service
@Transactional
public class ModelServiceImpl implements ModelService {

    private static final Logger LOG = LoggerFactory.getLogger(ModelServiceImpl.class);

    private final ModelRepository modelRepository;

    private final ModelMapper modelMapper;
    private final ModelAttachmentRepository modelAttachmentRepository;
    private final AttachmentMapper attachmentMapper;
    private final ModelAttachmentMapper modelAttachmentMapper;

    @Value("${minio.external}")
    private String BASE_URL;

    public ModelServiceImpl(ModelRepository modelRepository, ModelMapper modelMapper, ModelAttachmentRepository modelAttachmentRepository, AttachmentMapper attachmentMapperImpl, ModelAttachmentMapper modelAttachmentMapperImpl) {
        this.modelRepository = modelRepository;
        this.modelMapper = modelMapper;
        this.modelAttachmentRepository = modelAttachmentRepository;
        this.attachmentMapper = attachmentMapperImpl;
        this.modelAttachmentMapper = modelAttachmentMapperImpl;
    }

    @Override
    public ModelDTO save(ModelSaveDTO modelDTO) {
        LOG.info("Request to save Model : {}", modelDTO);
        Model model = modelMapper.toEntity(modelDTO);
        model = modelRepository.save(model);
        return modelMapper.toDto(model);
    }

    @Override
    public ModelDTO update(ModelDTO modelDTO) {
        LOG.debug("Request to update Model : {}", modelDTO);
        Model model = modelMapper.toEntity(modelDTO);
        model = modelRepository.save(model);
        return modelMapper.toDto(model);
    }

    @Override
    public Optional<ModelDTO> partialUpdate(ModelEditDTO modelEditDTO) {
        LOG.info("Request to partially update Model : {}", modelEditDTO);

        return modelRepository
            .findById(modelEditDTO.getId())
            .map(existingModel -> {
                modelMapper.partialUpdate(existingModel, modelEditDTO);

                return existingModel;
            })
            .map(modelRepository::save)
            .map(modelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ModelDTO> findAll(Pageable pageable) {
        LOG.info("Request to get all Models: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());

        Page<Model> models = modelRepository.findAll(pageable);

        Set<Long> modelIds = models.getContent().stream()
                .map(Model::getId)
                .collect(Collectors.toSet());

        Map<Long, List<Attachment>> modelAttachmentMap = modelIds.isEmpty()
        ? Collections.emptyMap()
        : modelAttachmentRepository.getModelMainPhotoByIds(modelIds)
                .stream()
                .collect(Collectors.groupingBy(
                        modelAttachment -> modelAttachment.getModel().getId(),
                        Collectors.mapping(ModelAttachment::getAttachment, Collectors.toList())
                ));

        return models.map(model -> {
                    ModelDTO dto =  modelMapper.toDto(model);
                    List<Attachment> attachments = modelAttachmentMap.get(dto.getId());
                    if (attachments != null && !attachments.isEmpty()) {
                        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachments.get(0));
                        attachmentDTO.setPath(BASE_URL + "/" + attachmentDTO.getPath());

                        ModelAttachmentDTO modelAttachmentDTO = new ModelAttachmentDTO();
                        modelAttachmentDTO.setMain(true);
                        modelAttachmentDTO.setAttachment(attachmentDTO);

                        dto.setModelAttachment(List.of(modelAttachmentDTO));
                    }
                    return dto;
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ModelDTO> findOne(Long id) {
        LOG.info("Request to get Model by id={}", id);

        return modelRepository.findById(id)
                .map(model -> {
                    ModelDTO modelDTO = modelMapper.toDto(model);
                    List<ModelAttachment> modelAttachments = modelAttachmentRepository.findAttachmentsByModelId(id);

                    if(!modelAttachments.isEmpty()) {

                        List<ModelAttachmentDTO> collect = modelAttachmentMapper.toDto(modelAttachments)
                                .stream()
                                .map(modelAttachmentDTO -> {
                                    AttachmentDTO attachment = modelAttachmentDTO.getAttachment();
                                    attachment.setPath(BASE_URL + "/" + attachment.getPath());
                                    modelAttachmentDTO.setAttachment(attachment);
                                    return modelAttachmentDTO;
                                }).collect(Collectors.toList());

                        modelDTO.setModelAttachment(collect);
                    }
                    return modelDTO;
                });
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Model : {}", id);
        modelRepository.deleteById(id);
    }
}
