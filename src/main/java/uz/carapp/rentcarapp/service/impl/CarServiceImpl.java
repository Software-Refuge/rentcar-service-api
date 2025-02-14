package uz.carapp.rentcarapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.carapp.rentcarapp.domain.Car;
import uz.carapp.rentcarapp.repository.CarRepository;
import uz.carapp.rentcarapp.service.CarService;
import uz.carapp.rentcarapp.service.dto.CarDTO;
import uz.carapp.rentcarapp.service.dto.CarEditDTO;
import uz.carapp.rentcarapp.service.dto.CarSaveDTO;
import uz.carapp.rentcarapp.service.mapper.CarMapper;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Car}.
 */
@Service
@Transactional
public class CarServiceImpl implements CarService {

    private static final Logger LOG = LoggerFactory.getLogger(CarServiceImpl.class);

    private final CarRepository carRepository;

    private final CarMapper carMapper;


    public CarServiceImpl(CarRepository carRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    @Override
    public CarDTO save(CarSaveDTO carSaveDTO) {
        LOG.info("Request to save Car : {}", carSaveDTO);
        Car car = carMapper.toEntity(carSaveDTO);
        car = carRepository.save(car);
        return carMapper.toDto(car);
    }

    @Override
    public CarDTO update(CarDTO carDTO) {
        LOG.debug("Request to update Car : {}", carDTO);
        Car car = carMapper.toEntity(carDTO);
        car = carRepository.save(car);
        return carMapper.toDto(car);
    }

    @Override
    public Optional<CarDTO> partialUpdate(CarEditDTO carEditDTO) {
        LOG.info("Request to partially update Car : {}", carEditDTO);

        return carRepository
            .findById(carEditDTO.getId())
            .map(existingCar -> {
                carMapper.partialUpdate(existingCar, carEditDTO);

                return existingCar;
            })
            .map(carRepository::save)
            .map(carMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CarDTO> findAll(Pageable pageable) {
        LOG.info("Request to get all Cars");
        return carRepository.findAll(pageable).map(carMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CarDTO> findOne(Long id) {
        LOG.info("Request to get Car : {}", id);
        return carRepository.findById(id).map(carMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.info("Request to delete Car : {}", id);
        carRepository.deleteById(id);
    }
}
