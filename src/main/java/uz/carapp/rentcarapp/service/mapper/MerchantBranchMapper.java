package uz.carapp.rentcarapp.service.mapper;

import org.mapstruct.*;
import uz.carapp.rentcarapp.domain.MerchantBranch;
import uz.carapp.rentcarapp.service.dto.MerchantBranchDTO;
import uz.carapp.rentcarapp.service.dto.MerchantBranchEditDTO;
import uz.carapp.rentcarapp.service.dto.MerchantBranchSaveDTO;

/**
 * Mapper for the entity {@link uz.carapp.rentcarapp.domain.MerchantBranch} and its DTO {@link uz.carapp.rentcarapp.service.dto.MerchantBranchDTO}.
 */
@Mapper(componentModel = "spring")
public interface MerchantBranchMapper extends EntityMapper<MerchantBranchDTO, MerchantBranch> {

    @Mapping(target = "merchant.id", source = "merchantId")
    MerchantBranch toEntity(MerchantBranchSaveDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "merchant",ignore = true)
    MerchantBranch partialUpdate(@MappingTarget MerchantBranch entity, MerchantBranchEditDTO dto);
}
