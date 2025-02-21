package uz.carapp.rentcarapp.service.mapper;

import org.mapstruct.*;
import uz.carapp.rentcarapp.domain.Brand;
import uz.carapp.rentcarapp.domain.Model;
import uz.carapp.rentcarapp.service.dto.*;

import java.io.File;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Mapper for the entity {@link Model} and its DTO {@link ModelDTO}.
 */
@Mapper(componentModel = "spring")
public interface ModelMapper extends EntityMapper<ModelDTO, Model> {
    @Mapping(target = "brand", source = "brand", qualifiedByName = "brandId")
    ModelDTO toDto(Model s);

    @Named("brandId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "status", source = "status")
    BrandDTO toDtoBrandId(Brand brand);

    @Mappings({
            @Mapping(target = "brand.id", source = "brandId")
    })
    Model toEntity(ModelSaveDTO modelSaveDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Model partialUpdate(@MappingTarget Model model, ModelEditDTO modelEditDTO);

    default ModelDTO toDto(Model model, String BASE_URL) {
        ModelDTO dto = toDto(model);

        dto.getModelAttachment()
                .stream()
                .map(modelAttachmentDTO -> {
                    AttachmentDTO attachment = modelAttachmentDTO.getAttachment();
                    attachment.setPath(BASE_URL + File.separator + attachment.getPath());
                    modelAttachmentDTO.setAttachment(attachment);
                    return modelAttachmentDTO;
                }).toList();

        return dto;
    }
}
