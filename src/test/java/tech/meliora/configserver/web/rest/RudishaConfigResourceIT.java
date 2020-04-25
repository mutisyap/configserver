package tech.meliora.configserver.web.rest;

import tech.meliora.configserver.ConfigserverApp;
import tech.meliora.configserver.domain.RudishaConfig;
import tech.meliora.configserver.repository.RudishaConfigRepository;
import tech.meliora.configserver.service.RudishaConfigService;
import tech.meliora.configserver.service.dto.RudishaConfigDTO;
import tech.meliora.configserver.service.mapper.RudishaConfigMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RudishaConfigResource} REST controller.
 */
@SpringBootTest(classes = ConfigserverApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class RudishaConfigResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DIGEST = "AAAAAAAAAA";
    private static final String UPDATED_DIGEST = "BBBBBBBBBB";

    private static final Long DEFAULT_LAST_UPDATED_ON = 1L;
    private static final Long UPDATED_LAST_UPDATED_ON = 2L;

    @Autowired
    private RudishaConfigRepository rudishaConfigRepository;

    @Autowired
    private RudishaConfigMapper rudishaConfigMapper;

    @Autowired
    private RudishaConfigService rudishaConfigService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRudishaConfigMockMvc;

    private RudishaConfig rudishaConfig;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RudishaConfig createEntity(EntityManager em) {
        RudishaConfig rudishaConfig = new RudishaConfig()
            .key(DEFAULT_KEY)
            .value(DEFAULT_VALUE)
            .digest(DEFAULT_DIGEST)
            .lastUpdatedOn(DEFAULT_LAST_UPDATED_ON);
        return rudishaConfig;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RudishaConfig createUpdatedEntity(EntityManager em) {
        RudishaConfig rudishaConfig = new RudishaConfig()
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE)
            .digest(UPDATED_DIGEST)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON);
        return rudishaConfig;
    }

    @BeforeEach
    public void initTest() {
        rudishaConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createRudishaConfig() throws Exception {
        int databaseSizeBeforeCreate = rudishaConfigRepository.findAll().size();

        // Create the RudishaConfig
        RudishaConfigDTO rudishaConfigDTO = rudishaConfigMapper.toDto(rudishaConfig);
        restRudishaConfigMockMvc.perform(post("/api/rudisha-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rudishaConfigDTO)))
            .andExpect(status().isCreated());

        // Validate the RudishaConfig in the database
        List<RudishaConfig> rudishaConfigList = rudishaConfigRepository.findAll();
        assertThat(rudishaConfigList).hasSize(databaseSizeBeforeCreate + 1);
        RudishaConfig testRudishaConfig = rudishaConfigList.get(rudishaConfigList.size() - 1);
        assertThat(testRudishaConfig.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testRudishaConfig.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testRudishaConfig.getDigest()).isEqualTo(DEFAULT_DIGEST);
        assertThat(testRudishaConfig.getLastUpdatedOn()).isEqualTo(DEFAULT_LAST_UPDATED_ON);
    }

    @Test
    @Transactional
    public void createRudishaConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rudishaConfigRepository.findAll().size();

        // Create the RudishaConfig with an existing ID
        rudishaConfig.setId(1L);
        RudishaConfigDTO rudishaConfigDTO = rudishaConfigMapper.toDto(rudishaConfig);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRudishaConfigMockMvc.perform(post("/api/rudisha-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rudishaConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RudishaConfig in the database
        List<RudishaConfig> rudishaConfigList = rudishaConfigRepository.findAll();
        assertThat(rudishaConfigList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = rudishaConfigRepository.findAll().size();
        // set the field null
        rudishaConfig.setKey(null);

        // Create the RudishaConfig, which fails.
        RudishaConfigDTO rudishaConfigDTO = rudishaConfigMapper.toDto(rudishaConfig);

        restRudishaConfigMockMvc.perform(post("/api/rudisha-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rudishaConfigDTO)))
            .andExpect(status().isBadRequest());

        List<RudishaConfig> rudishaConfigList = rudishaConfigRepository.findAll();
        assertThat(rudishaConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = rudishaConfigRepository.findAll().size();
        // set the field null
        rudishaConfig.setValue(null);

        // Create the RudishaConfig, which fails.
        RudishaConfigDTO rudishaConfigDTO = rudishaConfigMapper.toDto(rudishaConfig);

        restRudishaConfigMockMvc.perform(post("/api/rudisha-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rudishaConfigDTO)))
            .andExpect(status().isBadRequest());

        List<RudishaConfig> rudishaConfigList = rudishaConfigRepository.findAll();
        assertThat(rudishaConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRudishaConfigs() throws Exception {
        // Initialize the database
        rudishaConfigRepository.saveAndFlush(rudishaConfig);

        // Get all the rudishaConfigList
        restRudishaConfigMockMvc.perform(get("/api/rudisha-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rudishaConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].digest").value(hasItem(DEFAULT_DIGEST)))
            .andExpect(jsonPath("$.[*].lastUpdatedOn").value(hasItem(DEFAULT_LAST_UPDATED_ON.intValue())));
    }
    
    @Test
    @Transactional
    public void getRudishaConfig() throws Exception {
        // Initialize the database
        rudishaConfigRepository.saveAndFlush(rudishaConfig);

        // Get the rudishaConfig
        restRudishaConfigMockMvc.perform(get("/api/rudisha-configs/{id}", rudishaConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rudishaConfig.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.digest").value(DEFAULT_DIGEST))
            .andExpect(jsonPath("$.lastUpdatedOn").value(DEFAULT_LAST_UPDATED_ON.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRudishaConfig() throws Exception {
        // Get the rudishaConfig
        restRudishaConfigMockMvc.perform(get("/api/rudisha-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRudishaConfig() throws Exception {
        // Initialize the database
        rudishaConfigRepository.saveAndFlush(rudishaConfig);

        int databaseSizeBeforeUpdate = rudishaConfigRepository.findAll().size();

        // Update the rudishaConfig
        RudishaConfig updatedRudishaConfig = rudishaConfigRepository.findById(rudishaConfig.getId()).get();
        // Disconnect from session so that the updates on updatedRudishaConfig are not directly saved in db
        em.detach(updatedRudishaConfig);
        updatedRudishaConfig
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE)
            .digest(UPDATED_DIGEST)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON);
        RudishaConfigDTO rudishaConfigDTO = rudishaConfigMapper.toDto(updatedRudishaConfig);

        restRudishaConfigMockMvc.perform(put("/api/rudisha-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rudishaConfigDTO)))
            .andExpect(status().isOk());

        // Validate the RudishaConfig in the database
        List<RudishaConfig> rudishaConfigList = rudishaConfigRepository.findAll();
        assertThat(rudishaConfigList).hasSize(databaseSizeBeforeUpdate);
        RudishaConfig testRudishaConfig = rudishaConfigList.get(rudishaConfigList.size() - 1);
        assertThat(testRudishaConfig.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testRudishaConfig.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testRudishaConfig.getDigest()).isEqualTo(UPDATED_DIGEST);
        assertThat(testRudishaConfig.getLastUpdatedOn()).isEqualTo(UPDATED_LAST_UPDATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingRudishaConfig() throws Exception {
        int databaseSizeBeforeUpdate = rudishaConfigRepository.findAll().size();

        // Create the RudishaConfig
        RudishaConfigDTO rudishaConfigDTO = rudishaConfigMapper.toDto(rudishaConfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRudishaConfigMockMvc.perform(put("/api/rudisha-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rudishaConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RudishaConfig in the database
        List<RudishaConfig> rudishaConfigList = rudishaConfigRepository.findAll();
        assertThat(rudishaConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRudishaConfig() throws Exception {
        // Initialize the database
        rudishaConfigRepository.saveAndFlush(rudishaConfig);

        int databaseSizeBeforeDelete = rudishaConfigRepository.findAll().size();

        // Delete the rudishaConfig
        restRudishaConfigMockMvc.perform(delete("/api/rudisha-configs/{id}", rudishaConfig.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RudishaConfig> rudishaConfigList = rudishaConfigRepository.findAll();
        assertThat(rudishaConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
