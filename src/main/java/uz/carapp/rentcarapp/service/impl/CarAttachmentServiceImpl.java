package uz.carapp.rentcarapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.carapp.rentcarapp.domain.CarAttachment;
import uz.carapp.rentcarapp.repository.CarAttachmentRepository;
import uz.carapp.rentcarapp.service.CarAttachmentService;
import uz.carapp.rentcarapp.service.dto.CarAttachmentDTO;
import uz.carapp.rentcarapp.service.dto.CarAttachmentSaveDTO;
import uz.carapp.rentcarapp.service.mapper.CarAttachmentMapper;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CarAttachment}.
 */
@Service
@Transactional
public class CarAttachmentServiceImpl implements CarAttachmentService {

    private static final Logger LOG = LoggerFactory.getLogger(CarAttachmentServiceImpl.class);

    private final CarAttachmentRepository carAttachmentRepository;

    private final CarAttachmentMapper carAttachmentMapper;

    public CarAttachmentServiceImpl(
        CarAttachmentRepository carAttachmentRepository,
        CarAttachmentMapper carAttachmentMapper
    ) {
        this.carAttachmentRepository = carAttachmentRepository;
        this.carAttachmentMapper = carAttachmentMapper;
    }

    @Override
    public CarAttachmentDTO save(CarAttachmentSaveDTO carAttachmentSaveDTO) {
        LOG.info("Request to save CarAttachment : {}", carAttachmentSaveDTO);
        CarAttachment carAttachment = carAttachmentMapper.toEntity(carAttachmentSaveDTO);
        carAttachment = carAttachmentRepository.save(carAttachment);
        return carAttachmentMapper.toDto(carAttachment);
    }

    @Override
    public CarAttachmentDTO update(CarAttachmentDTO carAttachmentDTO) {
        LOG.debug("Request to update CarAttachment : {}", carAttachmentDTO);
        CarAttachment carAttachment = carAttachmentMapper.toEntity(carAttachmentDTO);
        carAttachment = carAttachmentRepository.save(carAttachment);
        return carAttachmentMapper.toDto(carAttachment);
    }

    @Override
    public Optional<CarAttachmentDTO> partialUpdate(CarAttachmentDTO carAttachmentDTO) {
        LOG.debug("Request to partially update CarAttachment : {}", carAttachmentDTO);

        return carAttachmentRepository
            .findById(carAttachmentDTO.getId())
            .map(existingCarAttachment -> {
                carAttachmentMapper.partialUpdate(existingCarAttachment, carAttachmentDTO);

                return existingCarAttachment;
            })
            .map(carAttachmentRepository::save)
            .map(carAttachmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CarAttachmentDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all CarAttachments");
        return carAttachmentRepository.findAll(pageable).map(carAttachmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CarAttachmentDTO> findOne(Long id) {
        LOG.debug("Request to get CarAttachment : {}", id);
        return carAttachmentRepository.findById(id).map(carAttachmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete CarAttachment : {}", id);
        carAttachmentRepository.deleteById(id);
    }

    @Override
    public void setMainPhoto(Long carId, Long attachmentId) {
        LOG.info("Request to set main photo for CarAttachment : {} and attachmentId:{}", carId,attachmentId);
        carAttachmentRepository.removeOldMainPhoto(carId);
        carAttachmentRepository.setMainPhoto(carId,attachmentId);
    }
}
