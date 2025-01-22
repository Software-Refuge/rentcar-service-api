package uz.carapp.rentcarapp.service.mapper;

import org.mapstruct.Mapper;
import uz.carapp.rentcarapp.domain.Parametr;
import uz.carapp.rentcarapp.service.dto.ParametrDTO;

/**
 * Mapper for the entity {@link Parametr} and its DTO {@link ParametrDTO}.
 */
@Mapper(componentModel = "spring")
public interface ParametrMapper extends EntityMapper<ParametrDTO, Parametr> {}
