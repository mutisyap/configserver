package tech.meliora.configserver.service;

import tech.meliora.configserver.domain.Application;
import tech.meliora.configserver.domain.Module;
import tech.meliora.configserver.domain.Profile;
import tech.meliora.configserver.domain.RudishaConfig;
import tech.meliora.configserver.repository.ApplicationRepository;
import tech.meliora.configserver.repository.ModuleRepository;
import tech.meliora.configserver.repository.ProfileRepository;
import tech.meliora.configserver.repository.RudishaConfigRepository;
import tech.meliora.configserver.service.dto.RudishaConfigDTO;
import tech.meliora.configserver.service.exception.DataIntegrityViolationException;
import tech.meliora.configserver.service.exception.EntityNotFoundException;
import tech.meliora.configserver.service.mapper.RudishaConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.meliora.configserver.service.util.SHAUtil;

import javax.annotation.PostConstruct;
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

    private final ApplicationRepository applicationRepository;

    private final ModuleRepository moduleRepository;

    private final ProfileRepository profileRepository;

    public SHAUtil shaUtil;

    public RudishaConfigService(RudishaConfigRepository rudishaConfigRepository, RudishaConfigMapper rudishaConfigMapper, ApplicationRepository applicationRepository, ModuleRepository moduleRepository, ProfileRepository profileRepository) {
        this.rudishaConfigRepository = rudishaConfigRepository;
        this.rudishaConfigMapper = rudishaConfigMapper;
        this.applicationRepository = applicationRepository;
        this.moduleRepository = moduleRepository;
        this.profileRepository = profileRepository;
    }

    @PostConstruct
    public void init() {
        String hashAlgorithm = "HmacSHA512";
        String hashSecret = "6ea312d4-877a-11ea-bc55-0242ac130003";

        shaUtil = new SHAUtil(hashAlgorithm, hashSecret);
    }

    /**
     * Save a rudishaConfig.
     *
     * @param rudishaConfigDTO the entity to save.
     * @return the persisted entity.
     */
    public RudishaConfigDTO save(RudishaConfigDTO rudishaConfigDTO) {
        log.debug("Request to save RudishaConfig : {}", rudishaConfigDTO);

        if (rudishaConfigDTO.getValue() == null) {
            rudishaConfigDTO.setValue("");
        }

        // set digest and last modified time
        rudishaConfigDTO.setLastUpdatedOn(System.currentTimeMillis());
        try {
            rudishaConfigDTO.setDigest(shaUtil.sign(rudishaConfigDTO.getValue()));
        } catch (Exception e) {
            log.warn("Unable to sign object : {}", rudishaConfigDTO, e);
        }

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

    @Transactional(readOnly = true)
    public List<RudishaConfigDTO> getConfigs(String applicationName, String moduleName, String profile) throws EntityNotFoundException, DataIntegrityViolationException {
        log.debug("Request to get all RudishaConfigs given application : {}, module : {}, profile : {}", applicationName, moduleName, profile);

        Application application = applicationRepository.findByName(applicationName);

        if (application == null) {
            throw new EntityNotFoundException("Application : " + applicationName + " Not Found");
        }

        Module module = moduleRepository.findByNameAndApplicationId(moduleName, application.getId());
        if (module == null) {
            throw new EntityNotFoundException("Module : " + moduleName + " Not Found");
        }

        Profile profileObject = profileRepository.findByNameAndModuleId(profile, module.getId());
        if (profileObject == null) {
            throw new EntityNotFoundException("Profile : " + profile + " Not Found");
        }

        // get configs by Profile
        List<RudishaConfig> rudishaConfigList = rudishaConfigRepository.findByProfileId(profileObject.getId());

        if (rudishaConfigList != null && !rudishaConfigList.isEmpty()) {
            // confirm integrity of configs
            for (RudishaConfig rudishaConfig : rudishaConfigList) {
                if (!shaUtil.validate(rudishaConfig.getValue(), rudishaConfig.getDigest(), log)) {
                    throw new DataIntegrityViolationException("Config Modified. Id = " + rudishaConfig.getId() + " key = : " + rudishaConfig.getKey());
                }
            }
        }

        List<RudishaConfigDTO> rudishaConfigDTOList = rudishaConfigMapper.toDto(rudishaConfigList);

        // prepare configs for return
        for (RudishaConfigDTO rudishaConfigDTO : rudishaConfigDTOList) {
            rudishaConfigDTO.setId(null);
            rudishaConfigDTO.setProfileId(null);
            rudishaConfigDTO.setProfileName(null);
        }

        return rudishaConfigDTOList;
    }
}
