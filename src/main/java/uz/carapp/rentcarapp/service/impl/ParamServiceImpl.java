package uz.carapp.rentcarapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.carapp.rentcarapp.domain.Param;
import uz.carapp.rentcarapp.repository.ParamRepository;
import uz.carapp.rentcarapp.service.ParamService;
import uz.carapp.rentcarapp.service.dto.ParamDTO;
import uz.carapp.rentcarapp.service.mapper.ParamMapper;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Param}.
 */
@Service
@Transactional
public class ParamServiceImpl implements ParamService {

    private static final Logger LOG = LoggerFactory.getLogger(ParamServiceImpl.class);

    private final ParamRepository paramRepository;

    private final ParamMapper paramMapper;

    public ParamServiceImpl(ParamRepository paramRepository, ParamMapper paramMapper) {
        this.paramRepository = paramRepository;
        this.paramMapper = paramMapper;
    }

    @Override
    public ParamDTO save(ParamDTO paramDTO) {
        LOG.debug("Request to save Param : {}", paramDTO);
        Param param = paramMapper.toEntity(paramDTO);
        param = paramRepository.save(param);
        return paramMapper.toDto(param);
    }

    @Override
    public ParamDTO update(ParamDTO paramDTO) {
        LOG.debug("Request to update Param : {}", paramDTO);
        Param param = paramMapper.toEntity(paramDTO);
        param = paramRepository.save(param);
        return paramMapper.toDto(param);
    }

    @Override
    public Optional<ParamDTO> partialUpdate(ParamDTO paramDTO) {
        LOG.debug("Request to partially update Param : {}", paramDTO);

        return paramRepository
            .findById(paramDTO.getId())
            .map(existingParam -> {
                paramMapper.partialUpdate(existingParam, paramDTO);

                return existingParam;
            })
            .map(paramRepository::save)
            .map(paramMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ParamDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Params");
        return paramRepository.findAll(pageable).map(paramMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ParamDTO> findOne(Long id) {
        LOG.debug("Request to get Param : {}", id);
        return paramRepository.findById(id).map(paramMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Param : {}", id);
        paramRepository.deleteById(id);
    }
}
