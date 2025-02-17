package uz.carapp.rentcarapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.carapp.rentcarapp.domain.Car;
import uz.carapp.rentcarapp.domain.CarParam;
import uz.carapp.rentcarapp.domain.Param;
import uz.carapp.rentcarapp.domain.ParamValue;
import uz.carapp.rentcarapp.domain.enumeration.LanguageEnum;
import uz.carapp.rentcarapp.repository.CarParamRepository;
import uz.carapp.rentcarapp.repository.CarRepository;
import uz.carapp.rentcarapp.repository.TranslationRepository;
import uz.carapp.rentcarapp.security.CustomUserDetails;
import uz.carapp.rentcarapp.service.CarService;
import uz.carapp.rentcarapp.service.dto.CarDTO;
import uz.carapp.rentcarapp.service.dto.CarEditDTO;
import uz.carapp.rentcarapp.service.dto.CarSaveDTO;
import uz.carapp.rentcarapp.service.mapper.CarMapper;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Car}.
 */
@Service
@Transactional
public class CarServiceImpl implements CarService {

    private static final Logger LOG = LoggerFactory.getLogger(CarServiceImpl.class);

    private final CarRepository carRepository;

    private final CarMapper carMapper;

    private final CarParamRepository carParamRepository;

    private final TranslationRepository translationRepository;


    public CarServiceImpl(CarRepository carRepository, CarMapper carMapper, CarParamRepository carParamRepository, TranslationRepository translationRepository) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
        this.carParamRepository = carParamRepository;
        this.translationRepository = translationRepository;
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


/*    public Page<CarDTO> findAll(Pageable pageable, String lang) {
        LOG.info("Request to get all Cars by lang:{}",lang);
        return carRepository.findAll(pageable).map(carMapper::toDto);
    }*/

    @Override
    @Transactional(readOnly = true)
    public Page<CarDTO> findAll(Pageable pageable, String lang) {
        Page<Car> cars = carRepository.findAll(pageable);
        List<Long> carIds = cars.stream().map(Car::getId).collect(Collectors.toList());

        // Barcha CarParam larni olish
        List<CarParam> carParams = carParamRepository.findByCarIds(carIds);

        // CarParam larni CarDTO ga qo‘shish
        Map<Long, Map<String, String>> carParamsMap = mapCarParams(carParams, lang);

        return cars.map(car -> {
            CarDTO dto = carMapper.toDto(car, lang, translationRepository);
            dto.setParams(carParamsMap.getOrDefault(car.getId(), new HashMap<>()));
            return dto;
        });
    }

    private Map<Long, Map<String, String>> mapCarParams(List<CarParam> carParams, String lang) {
        Map<Long, Map<String, String>> result = new HashMap<>();

        for (CarParam carParam : carParams) {
            Long carId = carParam.getCar().getId();
            String paramName = getTranslatedParam(carParam.getParam(), lang);
            String paramValue = getTranslatedParamValue(carParam.getParamValue(), carParam.getParamVal(), lang);

            result.computeIfAbsent(carId, k -> new HashMap<>()).put(paramName, paramValue);
        }
        return result;
    }

    private String getTranslatedParam(Param param, String lang) {
        return translationRepository.findTranslation("PARAM", param.getId(), LanguageEnum.valueOf(lang))
                .orElseGet(() -> param.getName());
    }

    private String getTranslatedParamValue(ParamValue paramValue, String paramVal, String lang) {
        if (paramValue != null) {
            return translationRepository.findTranslation("PARAM_VALUE", paramValue.getId(), LanguageEnum.valueOf(lang))
                    .orElseGet(() -> paramValue.getName());
        }
        return paramVal;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CarDTO> findOne(Long id, String lang) {
        LOG.info("Request to get Car : {}", id);
        List<CarParam> carParams = carParamRepository.findByCarIds(Collections.singletonList(id));

        Map<Long, Map<String, String>> carParamsMap = mapCarParams(carParams, lang);

        Optional<Car> car = carRepository.findById(id);

        if(car.isPresent()) {
            CarDTO dto = carMapper.toDto(car.get(), lang, translationRepository);
            dto.setParams(carParamsMap.getOrDefault(car.get().getId(), new HashMap<>()));
            return Optional.of(dto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void delete(Long id) {
        LOG.info("Request to delete Car : {}", id);
        carRepository.deleteById(id);
    }
}
