package uz.carapp.rentcarapp.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import uz.carapp.rentcarapp.domain.Attachment;
import uz.carapp.rentcarapp.domain.Model;
import uz.carapp.rentcarapp.domain.ModelAttachment;
import uz.carapp.rentcarapp.service.dto.AttachmentDTO;
import uz.carapp.rentcarapp.service.dto.ModelAttachmentDTO;
import uz.carapp.rentcarapp.service.dto.ModelAttachmentSaveDTO;
import uz.carapp.rentcarapp.service.dto.ModelDTO;

/**
 * Mapper for the entity {@link ModelAttachment} and its DTO {@link ModelAttachmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface ModelAttachmentMapper extends EntityMapper<ModelAttachmentDTO, ModelAttachment> {
    @Mapping(target = "model", source = "model", qualifiedByName = "modelId")
    @Mapping(target = "attachment", source = "attachment", qualifiedByName = "attachmentId")
    ModelAttachmentDTO toDto(ModelAttachment s);

    @Named("modelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ModelDTO toDtoModelId(Model model);

    @Named("attachmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "fileName", source = "fileName")
    @Mapping(target = "fileSize", source = "fileSize")
    @Mapping(target = "originalFileName", source = "originalFileName")
    @Mapping(target = "path", source = "path")
    @Mapping(target = "ext", source = "ext")
    AttachmentDTO toDtoAttachmentId(Attachment attachment);

    @Mapping(target = "model.id",source = "modelId")
    @Mapping(target = "attachment.id",source = "attachmentId")
    ModelAttachment toEntity(ModelAttachmentSaveDTO dto);
}
