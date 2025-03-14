package uz.carapp.rentcarapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.carapp.rentcarapp.domain.MerchantDocument;
import uz.carapp.rentcarapp.repository.MerchantDocumentRepository;
import uz.carapp.rentcarapp.service.MerchantDocumentService;
import uz.carapp.rentcarapp.service.dto.MerchantDocumentDTO;
import uz.carapp.rentcarapp.service.dto.MerchantDocumentSaveDTO;
import uz.carapp.rentcarapp.service.mapper.MerchantDocumentMapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link MerchantDocument}.
 */
@Service
@Transactional
public class MerchantDocumentServiceImpl implements MerchantDocumentService {

    private static final Logger LOG = LoggerFactory.getLogger(MerchantDocumentServiceImpl.class);

    private final MerchantDocumentRepository merchantDocumentRepository;

    private final MerchantDocumentMapper merchantDocumentMapper;


    public MerchantDocumentServiceImpl(
        MerchantDocumentRepository merchantDocumentRepository,
        MerchantDocumentMapper merchantDocumentMapper
    ) {
        this.merchantDocumentRepository = merchantDocumentRepository;
        this.merchantDocumentMapper = merchantDocumentMapper;
    }

    @Override
    public MerchantDocumentDTO save(MerchantDocumentSaveDTO merchantDocumentSaveDTO) {
        LOG.info("Request to save MerchantDocument : {}", merchantDocumentSaveDTO);
        MerchantDocument merchantDocument = merchantDocumentMapper.toEntity(merchantDocumentSaveDTO);
        merchantDocument = merchantDocumentRepository.save(merchantDocument);
        return merchantDocumentMapper.toDto(merchantDocument);
    }

    @Override
    public MerchantDocumentDTO update(MerchantDocumentDTO merchantDocumentDTO) {
        LOG.debug("Request to update MerchantDocument : {}", merchantDocumentDTO);
        MerchantDocument merchantDocument = merchantDocumentMapper.toEntity(merchantDocumentDTO);
        merchantDocument = merchantDocumentRepository.save(merchantDocument);
        return merchantDocumentMapper.toDto(merchantDocument);
    }

    @Override
    public Optional<MerchantDocumentDTO> partialUpdate(MerchantDocumentDTO merchantDocumentDTO) {
        LOG.debug("Request to partially update MerchantDocument : {}", merchantDocumentDTO);

        return merchantDocumentRepository
            .findById(merchantDocumentDTO.getId())
            .map(existingMerchantDocument -> {
                merchantDocumentMapper.partialUpdate(existingMerchantDocument, merchantDocumentDTO);

                return existingMerchantDocument;
            })
            .map(merchantDocumentRepository::save)
            .map(merchantDocumentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MerchantDocumentDTO> findAll() {
        LOG.debug("Request to get all MerchantDocuments");
        return merchantDocumentRepository
            .findAll()
            .stream()
            .map(merchantDocumentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MerchantDocumentDTO> findOne(Long id) {
        LOG.debug("Request to get MerchantDocument : {}", id);
        return merchantDocumentRepository.findById(id).map(merchantDocumentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete MerchantDocument : {}", id);
        merchantDocumentRepository.deleteById(id);
    }
}
