package uz.carapp.rentcarapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.carapp.rentcarapp.domain.CarTemplateParam;
import uz.carapp.rentcarapp.repository.CarTemplateParamRepository;
import uz.carapp.rentcarapp.service.CarTemplateParamService;
import uz.carapp.rentcarapp.service.dto.CarTemplateParamDTO;
import uz.carapp.rentcarapp.service.dto.CarTemplateParamSaveDTO;
import uz.carapp.rentcarapp.service.mapper.CarTemplateParamMapper;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CarTemplateParam}.
 */
@Service
@Transactional
public class CarTemplateParamServiceImpl implements CarTemplateParamService {

    private static final Logger LOG = LoggerFactory.getLogger(CarTemplateParamServiceImpl.class);

    private final CarTemplateParamRepository carTemplateParamRepository;

    private final CarTemplateParamMapper carTemplateParamMapper;

    public CarTemplateParamServiceImpl(
        CarTemplateParamRepository carTemplateParamRepository,
        CarTemplateParamMapper carTemplateParamMapper
    ) {
        this.carTemplateParamRepository = carTemplateParamRepository;
        this.carTemplateParamMapper = carTemplateParamMapper;
    }

    @Override
    public CarTemplateParamDTO save(CarTemplateParamSaveDTO carTemplateParamSaveDTO) {
        LOG.debug("Request to save CarTemplateParam : {}", carTemplateParamSaveDTO);
        CarTemplateParam carTemplateParam = carTemplateParamMapper.toEntity(carTemplateParamSaveDTO);
        carTemplateParam = carTemplateParamRepository.save(carTemplateParam);
        return carTemplateParamMapper.toDto(carTemplateParam);
    }

    @Override
    public CarTemplateParamDTO update(CarTemplateParamDTO carTemplateParamDTO) {
        LOG.info("Request to update CarTemplateParam : {}", carTemplateParamDTO);
        CarTemplateParam carTemplateParam = carTemplateParamMapper.toEntity(carTemplateParamDTO);
        carTemplateParam = carTemplateParamRepository.save(carTemplateParam);
        return carTemplateParamMapper.toDto(carTemplateParam);
    }

    @Override
    public Optional<CarTemplateParamDTO> partialUpdate(CarTemplateParamDTO carTemplateParamDTO) {
        LOG.info("Request to partially update CarTemplateParam : {}", carTemplateParamDTO);

        return carTemplateParamRepository
            .findById(carTemplateParamDTO.getId())
            .map(existingCarTemplateParam -> {
                carTemplateParamMapper.partialUpdate(existingCarTemplateParam, carTemplateParamDTO);

                return existingCarTemplateParam;
            })
            .map(carTemplateParamRepository::save)
            .map(carTemplateParamMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CarTemplateParamDTO> findAll(Pageable pageable) {
        LOG.info("Request to get all CarTemplateParams");
        return carTemplateParamRepository.findAll(pageable).map(carTemplateParamMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CarTemplateParamDTO> findOne(Long id) {
        LOG.info("Request to get CarTemplateParam : {}", id);
        return carTemplateParamRepository.findById(id).map(carTemplateParamMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.info("Request to delete CarTemplateParam : {}", id);
        carTemplateParamRepository.deleteById(id);
    }
}
