package uz.carapp.rentcarapp.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import uz.carapp.rentcarapp.domain.Car;
import uz.carapp.rentcarapp.domain.CarMileage;
import uz.carapp.rentcarapp.service.dto.CarDTO;
import uz.carapp.rentcarapp.service.dto.CarMileageDTO;
import uz.carapp.rentcarapp.service.dto.CarMileageSaveDTO;

/**
 * Mapper for the entity {@link CarMileage} and its DTO {@link CarMileageDTO}.
 */
@Mapper(componentModel = "spring")
public interface CarMileageMapper extends EntityMapper<CarMileageDTO, CarMileage> {
    @Mapping(target = "car", source = "car", qualifiedByName = "carId")
    CarMileageDTO toDto(CarMileage s);

    @Named("carId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CarDTO toDtoCarId(Car car);

    @Mapping(target = "car.id", source = "carId")
    CarMileage toEntity(CarMileageSaveDTO dto);
}
