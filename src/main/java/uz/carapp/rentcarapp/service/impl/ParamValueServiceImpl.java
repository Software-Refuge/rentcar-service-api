package uz.carapp.rentcarapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.carapp.rentcarapp.domain.ParamValue;
import uz.carapp.rentcarapp.repository.ParamValueRepository;
import uz.carapp.rentcarapp.service.ParamValueService;
import uz.carapp.rentcarapp.service.dto.ParamValueDTO;
import uz.carapp.rentcarapp.service.dto.ParamValueEditDTO;
import uz.carapp.rentcarapp.service.dto.ParamValueSaveDTO;
import uz.carapp.rentcarapp.service.mapper.ParamValueMapper;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ParamValue}.
 */
@Service
@Transactional
public class ParamValueServiceImpl implements ParamValueService {

    private static final Logger LOG = LoggerFactory.getLogger(ParamValueServiceImpl.class);

    private final ParamValueRepository paramValueRepository;

    private final ParamValueMapper paramValueMapper;

    public ParamValueServiceImpl(
        ParamValueRepository paramValueRepository,
        ParamValueMapper paramValueMapper
    ) {
        this.paramValueRepository = paramValueRepository;
        this.paramValueMapper = paramValueMapper;
    }

    @Override
    public ParamValueDTO save(ParamValueSaveDTO paramValueSaveDTO) {
        LOG.info("Request to save ParamValue : {}", paramValueSaveDTO);
        ParamValue paramValue = paramValueMapper.toEntity(paramValueSaveDTO);
        paramValue = paramValueRepository.save(paramValue);
        return paramValueMapper.toDto(paramValue);
    }

    @Override
    public ParamValueDTO update(ParamValueDTO paramValueDTO) {
        LOG.debug("Request to update ParamValue : {}", paramValueDTO);
        ParamValue paramValue = paramValueMapper.toEntity(paramValueDTO);
        paramValue = paramValueRepository.save(paramValue);
        return paramValueMapper.toDto(paramValue);
    }

    @Override
    public Optional<ParamValueDTO> partialUpdate(ParamValueEditDTO paramValueEditDTO) {
        LOG.info("Request to partially update ParamValue : {}", paramValueEditDTO);

        return paramValueRepository
            .findById(paramValueEditDTO.getId())
            .map(existingParamValue -> {
                paramValueMapper.partialUpdate(existingParamValue, paramValueEditDTO);

                return existingParamValue;
            })
            .map(paramValueRepository::save)
            .map(paramValueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ParamValueDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all ParamValues");
        return paramValueRepository.findAll(pageable).map(paramValueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ParamValueDTO> findOne(Long id) {
        LOG.debug("Request to get ParamValue : {}", id);
        return paramValueRepository.findById(id).map(paramValueMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ParamValue : {}", id);
        paramValueRepository.deleteById(id);
    }
}
