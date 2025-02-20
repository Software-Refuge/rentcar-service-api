package uz.carapp.rentcarapp.service.mapper;

import org.mapstruct.*;
import uz.carapp.rentcarapp.domain.Attachment;
import uz.carapp.rentcarapp.domain.Brand;
import uz.carapp.rentcarapp.service.dto.AttachmentDTO;
import uz.carapp.rentcarapp.service.dto.BrandDTO;
import uz.carapp.rentcarapp.service.dto.BrandEditDTO;
import uz.carapp.rentcarapp.service.dto.BrandSaveDTO;

/**
 * Mapper for the entity {@link Brand} and its DTO {@link BrandDTO}.
 */
@Mapper(componentModel = "spring")
public interface BrandMapper extends EntityMapper<BrandDTO, Brand> {
    @Mapping(target = "attachment", source = "attachment", qualifiedByName = "attachmentId")
    BrandDTO toDto(Brand s);

    @Named("attachmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "fileName", source = "fileName")
    @Mapping(target = "fileSize", source = "fileSize")
    @Mapping(target = "originalFileName", source = "originalFileName")
    @Mapping(target = "path", source = "path")
    @Mapping(target = "ext", source = "ext")
    AttachmentDTO toDtoAttachmentId(Attachment attachment);

    @Mapping(target = "attachment",source = "attachmentId", qualifiedByName = "mapAttachment")
    Brand toEntity(BrandSaveDTO dto);

    @Named("mapAttachment")
    default Attachment mapAttachment(Long attachmentId) {
        if(attachmentId == null) {
            return null;
        }

        Attachment attachment = new Attachment();
        attachment.setId(attachmentId);
        return attachment;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Brand partialUpdate(@MappingTarget Brand brand, BrandEditDTO brandEditDTO);
}
