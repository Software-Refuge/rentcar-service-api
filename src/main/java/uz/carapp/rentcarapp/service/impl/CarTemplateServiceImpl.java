package uz.carapp.rentcarapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.carapp.rentcarapp.domain.CarTemplate;
import uz.carapp.rentcarapp.repository.CarTemplateRepository;
import uz.carapp.rentcarapp.service.CarTemplateService;
import uz.carapp.rentcarapp.service.dto.CarTemplateDTO;
import uz.carapp.rentcarapp.service.dto.CarTemplateEditDTO;
import uz.carapp.rentcarapp.service.dto.CarTemplateSaveDTO;
import uz.carapp.rentcarapp.service.mapper.CarTemplateMapper;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CarTemplate}.
 */
@Service
@Transactional
public class CarTemplateServiceImpl implements CarTemplateService {

    private static final Logger LOG = LoggerFactory.getLogger(CarTemplateServiceImpl.class);

    private final CarTemplateRepository carTemplateRepository;

    private final CarTemplateMapper carTemplateMapper;

    public CarTemplateServiceImpl(
        CarTemplateRepository carTemplateRepository,
        CarTemplateMapper carTemplateMapper
    ) {
        this.carTemplateRepository = carTemplateRepository;
        this.carTemplateMapper = carTemplateMapper;
    }

    @Override
    public CarTemplateDTO save(CarTemplateSaveDTO carTemplateSaveDTO) {
        LOG.info("Request to save CarTemplate : {}", carTemplateSaveDTO);
        CarTemplate carTemplate = carTemplateMapper.toEntity(carTemplateSaveDTO);
        carTemplate = carTemplateRepository.save(carTemplate);
        return carTemplateMapper.toDto(carTemplate);
    }

    @Override
    public CarTemplateDTO update(CarTemplateDTO carTemplateDTO) {
        LOG.debug("Request to update CarTemplate : {}", carTemplateDTO);
        CarTemplate carTemplate = carTemplateMapper.toEntity(carTemplateDTO);
        carTemplate = carTemplateRepository.save(carTemplate);
        return carTemplateMapper.toDto(carTemplate);
    }

    @Override
    public Optional<CarTemplateDTO> partialUpdate(CarTemplateEditDTO carTemplateEditDTO) {
        LOG.info("Request to partially update CarTemplate : {}", carTemplateEditDTO);

        return carTemplateRepository
            .findById(carTemplateEditDTO.getId())
            .map(existingCarTemplate -> {
                carTemplateMapper.partialUpdate(existingCarTemplate, carTemplateEditDTO);

                return existingCarTemplate;
            })
            .map(carTemplateRepository::save)
            .map(carTemplateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CarTemplateDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all CarTemplates");
        return carTemplateRepository.findAll(pageable).map(carTemplateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CarTemplateDTO> findOne(Long id) {
        LOG.debug("Request to get CarTemplate : {}", id);
        return carTemplateRepository.findById(id).map(carTemplateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete CarTemplate : {}", id);
        carTemplateRepository.deleteById(id);
    }
}
