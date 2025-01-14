package uz.carapp.rentcarapp.service.mapper;

import org.mapstruct.Mapper;
import uz.carapp.rentcarapp.domain.Attachment;
import uz.carapp.rentcarapp.service.dto.AttachmentDTO;

@Mapper(componentModel = "spring")
public interface AttachmentMapper extends EntityMapper<AttachmentDTO, Attachment>{

    Attachment toEntity(AttachmentDTO attachmentDTO);
}
