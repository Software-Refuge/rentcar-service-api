package uz.carapp.rentcarapp.service.mapper;

import org.mapstruct.*;
import uz.carapp.rentcarapp.domain.Attachment;
import uz.carapp.rentcarapp.domain.Merchant;
import uz.carapp.rentcarapp.service.dto.MerchantDTO;
import uz.carapp.rentcarapp.service.dto.MerchantEditDTO;
import uz.carapp.rentcarapp.service.dto.MerchantSaveDTO;

/**
 * Mapper for the entity {@link Merchant} and its DTO {@link MerchantDTO}.
 */
@Mapper(componentModel = "spring")
public interface MerchantMapper extends EntityMapper<MerchantDTO, Merchant> {

    @Mapping(target = "user.id",source = "userId")
    @Mapping(target = "attachment", source = "attachmentId", qualifiedByName = "mapAttachment")
    Merchant toEntity(MerchantSaveDTO dto);

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
    Merchant partialUpdate(@MappingTarget Merchant entity, MerchantEditDTO dto);
}
