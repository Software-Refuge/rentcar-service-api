package uz.carapp.rentcarapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.carapp.rentcarapp.domain.CarMileage;
import uz.carapp.rentcarapp.domain.enumeration.MileageEnum;
import uz.carapp.rentcarapp.repository.CarMileageRepository;
import uz.carapp.rentcarapp.rest.errors.BadRequestCustomException;
import uz.carapp.rentcarapp.service.CarMileageService;
import uz.carapp.rentcarapp.service.dto.CarMileageDTO;
import uz.carapp.rentcarapp.service.dto.CarMileageSaveDTO;
import uz.carapp.rentcarapp.service.mapper.CarMileageMapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CarMileage}.
 */
@Service
@Transactional
public class CarMileageServiceImpl implements CarMileageService {

    private static final Logger LOG = LoggerFactory.getLogger(CarMileageServiceImpl.class);

    private final CarMileageRepository carMileageRepository;

    private final CarMileageMapper carMileageMapper;


    public CarMileageServiceImpl(
        CarMileageRepository carMileageRepository,
        CarMileageMapper carMileageMapper
    ) {
        this.carMileageRepository = carMileageRepository;
        this.carMileageMapper = carMileageMapper;
    }

    @Override
    public CarMileageDTO save(CarMileageSaveDTO carMileageSaveDTO) {
        LOG.info("Request to save CarMileage : {}", carMileageSaveDTO);

        Optional<MileageEnum> existingType = carMileageRepository.findMileageTypeByCarId(carMileageSaveDTO.getCarId());

        if (existingType.isPresent() && existingType.get() != carMileageSaveDTO.getUnit()) {
            throw new BadRequestCustomException("Mileage type mismatch! Previously stored as " + existingType.get(),"","");
        }
        CarMileage carMileage = carMileageMapper.toEntity(carMileageSaveDTO);
        carMileage = carMileageRepository.save(carMileage);
        return carMileageMapper.toDto(carMileage);
    }

    @Override
    public CarMileageDTO update(CarMileageDTO carMileageDTO) {
        LOG.info("Request to update CarMileage : {}", carMileageDTO);
        CarMileage carMileage = carMileageMapper.toEntity(carMileageDTO);
        carMileage = carMileageRepository.save(carMileage);
        return carMileageMapper.toDto(carMileage);
    }

    @Override
    public Optional<CarMileageDTO> partialUpdate(CarMileageDTO carMileageDTO) {
        LOG.info("Request to partially update CarMileage : {}", carMileageDTO);

        return carMileageRepository
            .findById(carMileageDTO.getId())
            .map(existingCarMileage -> {
                carMileageMapper.partialUpdate(existingCarMileage, carMileageDTO);

                return existingCarMileage;
            })
            .map(carMileageRepository::save)
            .map(carMileageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarMileageDTO> findAll(Long carId, Pageable pageable) {
        LOG.info("Request to get all CarMileages");
        return carMileageRepository.findAll(carId,pageable).stream().map(carMileageMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CarMileageDTO> findOne(Long id) {
        LOG.info("Request to get CarMileage : {}", id);
        return carMileageRepository.findById(id).map(carMileageMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.info("Request to delete CarMileage : {}", id);
        carMileageRepository.deleteById(id);
    }
}
