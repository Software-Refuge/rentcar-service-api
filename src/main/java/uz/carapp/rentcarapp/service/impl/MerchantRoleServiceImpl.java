package uz.carapp.rentcarapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.carapp.rentcarapp.domain.MerchantRole;
import uz.carapp.rentcarapp.repository.MerchantRoleRepository;
import uz.carapp.rentcarapp.service.MerchantRoleService;
import uz.carapp.rentcarapp.service.dto.MerchantRoleDTO;
import uz.carapp.rentcarapp.service.mapper.MerchantRoleMapper;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MerchantRole}.
 */
@Service
@Transactional
public class MerchantRoleServiceImpl implements MerchantRoleService {

    private static final Logger LOG = LoggerFactory.getLogger(MerchantRoleServiceImpl.class);

    private final MerchantRoleRepository merchantRoleRepository;

    private final MerchantRoleMapper merchantRoleMapper;

    public MerchantRoleServiceImpl(
        MerchantRoleRepository merchantRoleRepository,
        MerchantRoleMapper merchantRoleMapper
    ) {
        this.merchantRoleRepository = merchantRoleRepository;
        this.merchantRoleMapper = merchantRoleMapper;
    }

    @Override
    public MerchantRoleDTO save(MerchantRoleDTO merchantRoleDTO) {
        LOG.info("Request to save MerchantRole : {}", merchantRoleDTO);
        MerchantRole merchantRole = merchantRoleMapper.toEntity(merchantRoleDTO);
        merchantRole = merchantRoleRepository.save(merchantRole);
        return merchantRoleMapper.toDto(merchantRole);
    }

    @Override
    public MerchantRoleDTO update(MerchantRoleDTO merchantRoleDTO) {
        LOG.info("Request to update MerchantRole : {}", merchantRoleDTO);
        MerchantRole merchantRole = merchantRoleMapper.toEntity(merchantRoleDTO);
        merchantRole = merchantRoleRepository.save(merchantRole);
        return merchantRoleMapper.toDto(merchantRole);
    }

    @Override
    public Optional<MerchantRoleDTO> partialUpdate(MerchantRoleDTO merchantRoleDTO) {
        LOG.info("Request to partially update MerchantRole : {}", merchantRoleDTO);

        return merchantRoleRepository
            .findById(merchantRoleDTO.getId())
            .map(existingMerchantRole -> {
                merchantRoleMapper.partialUpdate(existingMerchantRole, merchantRoleDTO);

                return existingMerchantRole;
            })
            .map(merchantRoleRepository::save)
            .map(merchantRoleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MerchantRoleDTO> findAll(Pageable pageable) {
        LOG.info("Request to get all MerchantRoles");
        return merchantRoleRepository.findAll(pageable).map(merchantRoleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MerchantRoleDTO> findOne(Long id) {
        LOG.debug("Request to get MerchantRole : {}", id);
        return merchantRoleRepository.findById(id).map(merchantRoleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.info("Request to delete MerchantRole : {}", id);
        merchantRoleRepository.deleteById(id);
    }
}
