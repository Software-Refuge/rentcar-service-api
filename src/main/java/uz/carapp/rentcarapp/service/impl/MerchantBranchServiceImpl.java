package uz.carapp.rentcarapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import uz.carapp.rentcarapp.domain.MerchantBranch;
import uz.carapp.rentcarapp.repository.MerchantBranchRepository;
import uz.carapp.rentcarapp.service.MerchantBranchService;
import uz.carapp.rentcarapp.service.dto.MerchantBranchDTO;
import uz.carapp.rentcarapp.service.dto.MerchantBranchEditDTO;
import uz.carapp.rentcarapp.service.dto.MerchantBranchSaveDTO;
import uz.carapp.rentcarapp.service.mapper.MerchantBranchMapper;

import java.util.Optional;

/**
 * Service Implementation for managing {@link uz.carapp.rentcarapp.domain.MerchantBranch}.
 */
@Service
@Transactional
public class MerchantBranchServiceImpl implements MerchantBranchService {

    private static final Logger LOG = LoggerFactory.getLogger(MerchantBranchServiceImpl.class);

    private final MerchantBranchRepository merchantBranchRepository;

    private final MerchantBranchMapper merchantBranchMapper;

    public MerchantBranchServiceImpl(MerchantBranchRepository merchantBranchRepository, MerchantBranchMapper merchantBranchMapper) {
        this.merchantBranchRepository = merchantBranchRepository;
        this.merchantBranchMapper = merchantBranchMapper;
    }

    @Override
    public MerchantBranchDTO save(MerchantBranchSaveDTO merchantBranchSaveDTO) {
        LOG.info("Request to save MerchantBranch : {}", merchantBranchSaveDTO);
        MerchantBranch merchantBranch = merchantBranchMapper.toEntity(merchantBranchSaveDTO);
        merchantBranch = merchantBranchRepository.save(merchantBranch);
        return merchantBranchMapper.toDto(merchantBranch);
    }

    @Override
    public Optional<MerchantBranchDTO> partialUpdate(MerchantBranchEditDTO merchantBranchEditDTO) {
        LOG.info("Request to partially update MerchantBranch : {}", merchantBranchEditDTO);

        return merchantBranchRepository
            .findById(merchantBranchEditDTO.getId())
            .map(existingMerchant -> {
                merchantBranchMapper.partialUpdate(existingMerchant, merchantBranchEditDTO);

                return existingMerchant;
            })
            .map(merchantBranchRepository::save)
            .map(merchantBranchMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MerchantBranchDTO> findAll(String search, Pageable pageable) {
        LOG.info("Request to get all Merchants");
        if(StringUtils.hasText(search))
            search = search.toLowerCase();
        return merchantBranchRepository.findAll(search, pageable).map(merchantBranchMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MerchantBranchDTO> findOne(Long id) {
        LOG.info("Request to get Merchant : {}", id);
        return merchantBranchRepository.findById(id).map(merchantBranchMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.info("Request to delete Merchant : {}", id);
        merchantBranchRepository.deleteById(id);
    }
}
