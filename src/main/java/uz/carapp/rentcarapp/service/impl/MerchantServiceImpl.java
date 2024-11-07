package uz.carapp.rentcarapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.carapp.rentcarapp.domain.Merchant;
import uz.carapp.rentcarapp.repository.MerchantRepository;
import uz.carapp.rentcarapp.service.MerchantService;
import uz.carapp.rentcarapp.service.dto.MerchantDTO;
import uz.carapp.rentcarapp.service.mapper.MerchantMapper;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Merchant}.
 */
@Service
@Transactional
public class MerchantServiceImpl implements MerchantService {

    private static final Logger LOG = LoggerFactory.getLogger(MerchantServiceImpl.class);

    private final MerchantRepository merchantRepository;

    private final MerchantMapper merchantMapper;

    public MerchantServiceImpl(
        MerchantRepository merchantRepository,
        MerchantMapper merchantMapper
    ) {
        this.merchantRepository = merchantRepository;
        this.merchantMapper = merchantMapper;
    }

    @Override
    public MerchantDTO save(MerchantDTO merchantDTO) {
        LOG.info("Request to save Merchant : {}", merchantDTO);
        Merchant merchant = merchantMapper.toEntity(merchantDTO);
        merchant = merchantRepository.save(merchant);
        return merchantMapper.toDto(merchant);
    }

    @Override
    public Optional<MerchantDTO> partialUpdate(MerchantDTO merchantDTO) {
        LOG.info("Request to partially update Merchant : {}", merchantDTO);

        return merchantRepository
            .findById(merchantDTO.getId())
            .map(existingMerchant -> {
                merchantMapper.partialUpdate(existingMerchant, merchantDTO);

                return existingMerchant;
            })
            .map(merchantRepository::save)
            .map(merchantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MerchantDTO> findAll(Pageable pageable) {
        LOG.info("Request to get all Merchants");
        return merchantRepository.findAll(pageable).map(merchantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MerchantDTO> findOne(Long id) {
        LOG.info("Request to get Merchant : {}", id);
        return merchantRepository.findById(id).map(merchantMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.info("Request to delete Merchant : {}", id);
        merchantRepository.deleteById(id);
    }
}
