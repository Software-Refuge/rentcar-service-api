package uz.carapp.rentcarapp.service.mapper;

import org.mapstruct.*;
import uz.carapp.rentcarapp.domain.Param;
import uz.carapp.rentcarapp.domain.ParamValue;
import uz.carapp.rentcarapp.service.dto.ParamDTO;
import uz.carapp.rentcarapp.service.dto.ParamValueDTO;
import uz.carapp.rentcarapp.service.dto.ParamValueEditDTO;
import uz.carapp.rentcarapp.service.dto.ParamValueSaveDTO;

/**
 * Mapper for the entity {@link ParamValue} and its DTO {@link ParamValueDTO}.
 */
@Mapper(componentModel = "spring")
public interface ParamValueMapper extends EntityMapper<ParamValueDTO, ParamValue> {
    @Mapping(target = "param", source = "param", qualifiedByName = "paramId")
    ParamValueDTO toDto(ParamValue s);

    @Named("paramId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ParamDTO toDtoParamId(Param param);

    @Mappings({
            @Mapping(target = "param.id", source = "paramId")
    })
    ParamValue toEntity(ParamValueSaveDTO paramValueSaveDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ParamValue partialUpdate(@MappingTarget ParamValue paramValue, ParamValueEditDTO paramValueEditDTO);
}
