package uz.carapp.rentcarapp.service.mapper;

import org.mapstruct.Mapper;
import uz.carapp.rentcarapp.domain.Document;
import uz.carapp.rentcarapp.service.dto.DocumentDTO;
import uz.carapp.rentcarapp.service.dto.DocumentSaveDTO;

/**
 * Mapper for the entity {@link Document} and its DTO {@link DocumentDTO}.
 */
@Mapper(componentModel = "spring")
public interface DocumentMapper extends EntityMapper<DocumentDTO, Document> {

    Document toEntity(DocumentSaveDTO dto);
}
