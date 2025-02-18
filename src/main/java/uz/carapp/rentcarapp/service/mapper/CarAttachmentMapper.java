package uz.carapp.rentcarapp.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import uz.carapp.rentcarapp.domain.Attachment;
import uz.carapp.rentcarapp.domain.Car;
import uz.carapp.rentcarapp.domain.CarAttachment;
import uz.carapp.rentcarapp.service.dto.AttachmentDTO;
import uz.carapp.rentcarapp.service.dto.CarAttachmentDTO;
import uz.carapp.rentcarapp.service.dto.CarAttachmentSaveDTO;
import uz.carapp.rentcarapp.service.dto.CarDTO;

/**
 * Mapper for the entity {@link CarAttachment} and its DTO {@link CarAttachmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface CarAttachmentMapper extends EntityMapper<CarAttachmentDTO, CarAttachment> {
    @Mapping(target = "car", source = "car", qualifiedByName = "carId")
    @Mapping(target = "attachment", source = "attachment", qualifiedByName = "attachmentId")
    CarAttachmentDTO toDto(CarAttachment s);

    @Named("carId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CarDTO toDtoCarId(Car car);

    @Named("attachmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AttachmentDTO toDtoAttachmentId(Attachment attachment);

    @Mapping(target = "car.id", source = "carId")
    @Mapping(target = "attachment.id", source = "attachmentId")
    CarAttachment toEntity(CarAttachmentSaveDTO dto);
}
