package uz.carapp.rentcarapp.service.mapper;

import org.mapstruct.*;
import uz.carapp.rentcarapp.domain.*;
import uz.carapp.rentcarapp.domain.enumeration.LanguageEnum;
import uz.carapp.rentcarapp.repository.TranslationRepository;
import uz.carapp.rentcarapp.service.dto.*;

import java.util.List;

/**
 * Mapper for the entity {@link Car} and its DTO {@link CarDTO}.
 */
@Mapper(componentModel = "spring")
public interface CarMapper extends EntityMapper<CarDTO, Car> {
    @Mapping(target = "model", source = "model", qualifiedByName = "modelId")
    @Mapping(target = "merchant", source = "merchant", qualifiedByName = "merchantId")
    @Mapping(target = "merchantBranch", source = "merchantBranch", qualifiedByName = "merchantBranchId")
    CarDTO toDto(Car s);

    @Named("modelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ModelDTO toDtoModelId(Model model);

    @Named("merchantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "companyName", source = "companyName")
    MerchantDTO toDtoMerchantId(Merchant merchant);

    @Named("merchantBranchId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    MerchantBranchDTO toDtoMerchantBranchId(MerchantBranch merchantBranch);

    @Mapping(target = "model.id", source = "modelId")
    @Mapping(target = "merchant.id", source = "merchantId")
    @Mapping(target = "merchantBranch.id", source = "merchantBranchId")
    Car toEntity(CarSaveDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Car partialUpdate(@MappingTarget Car car, CarEditDTO carEditDTO);

    /**
     * **Tarjima qo'shish uchun maxsus metod**
     */
    default CarDTO toDto(Car car, String lang, TranslationRepository translationRepository) {
        CarDTO dto = toDto(car);

        // Model nomini tarjima qilish
        dto.getModel().setName(
                translationRepository.findTranslationByFieldName("MODEL", car.getModel().getName(), LanguageEnum.valueOf(lang))
                        .orElse(car.getModel().getName())
        );

        return dto;
    }
}
