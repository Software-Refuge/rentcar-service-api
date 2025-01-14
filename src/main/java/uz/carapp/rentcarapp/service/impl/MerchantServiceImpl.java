package uz.carapp.rentcarapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import uz.carapp.rentcarapp.domain.Merchant;
import uz.carapp.rentcarapp.repository.MerchantRepository;
import uz.carapp.rentcarapp.repository.UserRepository;
import uz.carapp.rentcarapp.rest.errors.BadRequestCustomException;
import uz.carapp.rentcarapp.service.MerchantService;
import uz.carapp.rentcarapp.service.dto.MerchantDTO;
import uz.carapp.rentcarapp.service.dto.MerchantSaveDTO;
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
    private final UserRepository userRepository;

    public MerchantServiceImpl(
        MerchantRepository merchantRepository,
        MerchantMapper merchantMapper,
        UserRepository userRepository) {
        this.merchantRepository = merchantRepository;
        this.merchantMapper = merchantMapper;
        this.userRepository = userRepository;
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
