package uz.carapp.rentcarapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.carapp.rentcarapp.domain.Color;
import uz.carapp.rentcarapp.repository.ColorRepository;
import uz.carapp.rentcarapp.service.ColorService;
import uz.carapp.rentcarapp.service.dto.ColorDTO;
import uz.carapp.rentcarapp.service.mapper.ColorMapper;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Color}.
 */
@Service
@Transactional
public class ColorServiceImpl implements ColorService {

    private static final Logger LOG = LoggerFactory.getLogger(ColorServiceImpl.class);

    private final ColorRepository colorRepository;

    private final ColorMapper colorMapper;

    public ColorServiceImpl(ColorRepository colorRepository, ColorMapper colorMapper) {
        this.colorRepository = colorRepository;
        this.colorMapper = colorMapper;
    }

    @Override
    public ColorDTO save(ColorDTO colorDTO) {
        LOG.debug("Request to save Color : {}", colorDTO);
        Color color = colorMapper.toEntity(colorDTO);
        color = colorRepository.save(color);
        return colorMapper.toDto(color);
    }

    @Override
    public ColorDTO update(ColorDTO colorDTO) {
        LOG.debug("Request to update Color : {}", colorDTO);
        Color color = colorMapper.toEntity(colorDTO);
        color = colorRepository.save(color);
        return colorMapper.toDto(color);
    }

    @Override
    public Optional<ColorDTO> partialUpdate(ColorDTO colorDTO) {
        LOG.debug("Request to partially update Color : {}", colorDTO);

        return colorRepository
            .findById(colorDTO.getId())
            .map(existingColor -> {
                colorMapper.partialUpdate(existingColor, colorDTO);

                return existingColor;
            })
            .map(colorRepository::save)
            .map(colorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ColorDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Colors");
        return colorRepository.findAll(pageable).map(colorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ColorDTO> findOne(Long id) {
        LOG.debug("Request to get Color : {}", id);
        return colorRepository.findById(id).map(colorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Color : {}", id);
        colorRepository.deleteById(id);
    }
}
