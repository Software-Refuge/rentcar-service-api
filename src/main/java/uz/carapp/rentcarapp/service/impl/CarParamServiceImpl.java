package uz.carapp.rentcarapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.carapp.rentcarapp.domain.CarParam;
import uz.carapp.rentcarapp.repository.CarParamRepository;
import uz.carapp.rentcarapp.service.CarParamService;
import uz.carapp.rentcarapp.service.dto.CarParamDTO;
import uz.carapp.rentcarapp.service.dto.CarParamEditDTO;
import uz.carapp.rentcarapp.service.dto.CarParamSaveDTO;
import uz.carapp.rentcarapp.service.mapper.CarParamMapper;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CarParam}.
 */
@Service
@Transactional
public class CarParamServiceImpl implements CarParamService {

    private static final Logger LOG = LoggerFactory.getLogger(CarParamServiceImpl.class);

    private final CarParamRepository carParamRepository;

    private final CarParamMapper carParamMapper;


    public CarParamServiceImpl(
        CarParamRepository carParamRepository,
        CarParamMapper carParamMapper
    ) {
        this.carParamRepository = carParamRepository;
        this.carParamMapper = carParamMapper;
    }

    @Override
    public CarParamDTO save(CarParamSaveDTO carParamSaveDTO) {
        LOG.info("Request to save CarParam : {}", carParamSaveDTO);
        CarParam carParam = carParamMapper.toEntity(carParamSaveDTO);
        carParam = carParamRepository.save(carParam);
        return carParamMapper.toDto(carParam);
    }

    @Override
    public CarParamDTO update(CarParamDTO carParamDTO) {
        LOG.debug("Request to update CarParam : {}", carParamDTO);
        CarParam carParam = carParamMapper.toEntity(carParamDTO);
        carParam = carParamRepository.save(carParam);
        return carParamMapper.toDto(carParam);
    }

    @Override
    public Optional<CarParamDTO> partialUpdate(CarParamEditDTO carParamEditDTO) {
        LOG.info("Request to partially update CarParam : {}", carParamEditDTO);

        return carParamRepository
            .findById(carParamEditDTO.getId())
            .map(existingCarParam -> {
                carParamMapper.partialUpdate(existingCarParam, carParamEditDTO);

                return existingCarParam;
            })
            .map(carParamRepository::save)
            .map(carParamMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CarParamDTO> findAll(Pageable pageable) {
        LOG.info("Request to get all CarParams");
        return carParamRepository.findAll(pageable).map(carParamMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CarParamDTO> findOne(Long id) {
        LOG.info("Request to get CarParam : {}", id);
        return carParamRepository.findById(id).map(carParamMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.info("Request to delete CarParam : {}", id);
        carParamRepository.deleteById(id);
    }
}
