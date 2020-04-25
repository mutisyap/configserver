package tech.meliora.configserver.service;

import tech.meliora.configserver.domain.RudishaConfig;
import tech.meliora.configserver.repository.RudishaConfigRepository;
import tech.meliora.configserver.service.dto.RudishaConfigDTO;
import tech.meliora.configserver.service.mapper.RudishaConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link RudishaConfig}.
 */
@Service
@Transactional
public class RudishaConfigService {

    private final Logger log = LoggerFactory.getLogger(RudishaConfigService.class);

    private final RudishaConfigRepository rudishaConfigRepository;

    private final RudishaConfigMapper rudishaConfigMapper;

    public RudishaConfigService(RudishaConfigRepository rudishaConfigRepository, RudishaConfigMapper rudishaConfigMapper) {
        this.rudishaConfigRepository = rudishaConfigRepository;
        this.rudishaConfigMapper = rudishaConfigMapper;
    }

    /**
     * Save a rudishaConfig.
     *
     * @param rudishaConfigDTO the entity to save.
     * @return the persisted entity.
     */
    public RudishaConfigDTO save(RudishaConfigDTO rudishaConfigDTO) {
        log.debug("Request to save RudishaConfig : {}", rudishaConfigDTO);
        RudishaConfig rudishaConfig = rudishaConfigMapper.toEntity(rudishaConfigDTO);
        rudishaConfig = rudishaConfigRepository.save(rudishaConfig);
        return rudishaConfigMapper.toDto(rudishaConfig);
    }

    /**
     * Get all the rudishaConfigs.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RudishaConfigDTO> findAll() {
        log.debug("Request to get all RudishaConfigs");
        return rudishaConfigRepository.findAll().stream()
            .map(rudishaConfigMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one rudishaConfig by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RudishaConfigDTO> findOne(Long id) {
        log.debug("Request to get RudishaConfig : {}", id);
        return rudishaConfigRepository.findById(id)
            .map(rudishaConfigMapper::toDto);
    }

    /**
     * Delete the rudishaConfig by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RudishaConfig : {}", id);
        rudishaConfigRepository.deleteById(id);
    }
}
