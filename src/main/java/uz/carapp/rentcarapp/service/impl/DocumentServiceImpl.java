package uz.carapp.rentcarapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.carapp.rentcarapp.domain.Document;
import uz.carapp.rentcarapp.repository.DocumentRepository;
import uz.carapp.rentcarapp.service.DocumentService;
import uz.carapp.rentcarapp.service.dto.DocumentDTO;
import uz.carapp.rentcarapp.service.dto.DocumentSaveDTO;
import uz.carapp.rentcarapp.service.mapper.DocumentMapper;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Document}.
 */
@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentServiceImpl.class);

    private final DocumentRepository documentRepository;

    private final DocumentMapper documentMapper;


    public DocumentServiceImpl(
        DocumentRepository documentRepository,
        DocumentMapper documentMapper
    ) {
        this.documentRepository = documentRepository;
        this.documentMapper = documentMapper;
    }

    @Override
    public DocumentDTO save(DocumentSaveDTO documentSaveDTO) {
        LOG.info("Request to save Document : {}", documentSaveDTO);
        Document document = documentMapper.toEntity(documentSaveDTO);
        document = documentRepository.save(document);
        return documentMapper.toDto(document);
    }

    @Override
    public DocumentDTO update(DocumentDTO documentDTO) {
        LOG.debug("Request to update Document : {}", documentDTO);
        Document document = documentMapper.toEntity(documentDTO);
        document = documentRepository.save(document);
        return documentMapper.toDto(document);
    }

    @Override
    public Optional<DocumentDTO> partialUpdate(DocumentDTO documentDTO) {
        LOG.debug("Request to partially update Document : {}", documentDTO);

        return documentRepository
            .findById(documentDTO.getId())
            .map(existingDocument -> {
                documentMapper.partialUpdate(existingDocument, documentDTO);

                return existingDocument;
            })
            .map(documentRepository::save)
            .map(documentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DocumentDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Documents");
        return documentRepository.findAll(pageable).map(documentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DocumentDTO> findOne(Long id) {
        LOG.debug("Request to get Document : {}", id);
        return documentRepository.findById(id).map(documentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Document : {}", id);
        documentRepository.deleteById(id);
    }
}
