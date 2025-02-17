package uz.carapp.rentcarapp.service.mapper;

import org.mapstruct.*;
import uz.carapp.rentcarapp.domain.Car;
import uz.carapp.rentcarapp.domain.CarParam;
import uz.carapp.rentcarapp.domain.Param;
import uz.carapp.rentcarapp.domain.ParamValue;
import uz.carapp.rentcarapp.service.dto.*;

/**
 * Mapper for the entity {@link CarParam} and its DTO {@link CarParamDTO}.
 */
@Mapper(componentModel = "spring")
public interface CarParamMapper extends EntityMapper<CarParamDTO, CarParam> {
    @Mapping(target = "car", source = "car", qualifiedByName = "carId")
    @Mapping(target = "param", source = "param", qualifiedByName = "paramId")
    CarParamDTO toDto(CarParam s);

    @Named("carId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CarDTO toDtoCarId(Car car);

    @Named("paramId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ParamDTO toDtoParamId(Param param);

    @Named("mapParamValue")
    default ParamValue mapParamValue(Long paramValueId)  {
        if(paramValueId == null) {
            return null;
        }

        ParamValue paramValue = new ParamValue();
        paramValue.setId(paramValueId);
        return paramValue;
    }

    @Named("paramValueId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id",source = "id")
    ParamValueDTO toDToParamValueId(ParamValue paramValue);


    @Mapping(target = "car.id", source = "carId")
    @Mapping(target = "param.id", source = "paramId")
    @Mapping(target = "paramValue", source = "paramValueId", qualifiedByName = "mapParamValue")
    CarParam toEntity(CarParamSaveDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CarParam partialUpdate(@MappingTarget CarParam carParam, CarParamEditDTO carParamEditDTO);
}
