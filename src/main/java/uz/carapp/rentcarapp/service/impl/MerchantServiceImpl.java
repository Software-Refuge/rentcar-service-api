package uz.carapp.rentcarapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import uz.carapp.rentcarapp.domain.Merchant;
import uz.carapp.rentcarapp.domain.MerchantRole;
import uz.carapp.rentcarapp.domain.enumeration.MerchantRoleEnum;
import uz.carapp.rentcarapp.repository.MerchantRepository;
import uz.carapp.rentcarapp.repository.MerchantRoleRepository;
import uz.carapp.rentcarapp.repository.UserRepository;
import uz.carapp.rentcarapp.rest.errors.BadRequestCustomException;
import uz.carapp.rentcarapp.security.UserDetailsServiceImpl;
import uz.carapp.rentcarapp.service.MerchantService;
import uz.carapp.rentcarapp.service.dto.*;
import uz.carapp.rentcarapp.service.mapper.MerchantMapper;
import uz.carapp.rentcarapp.service.mapper.MerchantRoleMapper;
import uz.carapp.rentcarapp.service.mapper.MerchantRoleMapperImpl;

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
    private final UserRepository userRepository;
    private final MerchantRoleRepository merchantRoleRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final MerchantRoleMapper merchantRoleMapper;

    public MerchantServiceImpl(
            MerchantRepository merchantRepository,
            MerchantMapper merchantMapper,
            UserRepository userRepository, MerchantRoleRepository merchantRoleRepository, UserDetailsServiceImpl userDetailsService, MerchantRoleMapper merchantRoleMapper) {
        this.merchantRepository = merchantRepository;
        this.merchantMapper = merchantMapper;
        this.userRepository = userRepository;
        this.merchantRoleRepository = merchantRoleRepository;
        this.userDetailsService = userDetailsService;
        this.merchantRoleMapper = merchantRoleMapper;
    }

    @Override
    public MerchantDTO save(MerchantSaveDTO merchantSaveDTO) {
        LOG.info("Request to save Merchant : {}", merchantSaveDTO);

        //check user already attached to merchant
        if(userRepository.checkUserAssignedToMerchant(merchantSaveDTO.getUserId())>0) {
            throw new BadRequestCustomException("User already assigned other merchant","Merchant","M001");
        }

        //check user has USER role
        if(userRepository.checkHasUserRole(merchantSaveDTO.getUserId())==0) {
            throw new BadRequestCustomException("User has no 'USER' role","Merchant","M002");
        }

        Merchant merchant = merchantMapper.toEntity(merchantSaveDTO);
        merchant = merchantRepository.save(merchant);

        //assign OWNER ROLE to user
        MerchantRoleDTO merchantRoleDTO = new MerchantRoleDTO();
        merchantRoleDTO.setMerchant(merchantMapper.toDto(merchant));
        merchantRoleDTO.setUser(new UserDTO(merchantSaveDTO.getUserId()));
        merchantRoleDTO.setMerchantRoleType(MerchantRoleEnum.ROLE_OWNER);
        merchantRoleRepository.save(merchantRoleMapper.toEntity(merchantRoleDTO));

        return merchantMapper.toDto(merchant);
    }

    @Override
    public Optional<MerchantDTO> partialUpdate(MerchantEditDTO merchantEditDTO) {
        LOG.info("Request to partially update Merchant : {}", merchantEditDTO);

        return merchantRepository
            .findById(merchantEditDTO.getId())
            .map(existingMerchant -> {
                merchantMapper.partialUpdate(existingMerchant, merchantEditDTO);

                return existingMerchant;
            })
            .map(merchantRepository::save)
            .map(merchantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MerchantDTO> findAll(String search, Pageable pageable) {
        LOG.info("Request to get all Merchants");
        if(StringUtils.hasText(search))
            search = search.toLowerCase();
        return merchantRepository.findAll(search, pageable).map(merchantMapper::toDto);
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
