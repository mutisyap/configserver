package tech.meliora.configserver.web.rest;

import tech.meliora.configserver.service.RudishaConfigService;
import tech.meliora.configserver.web.rest.errors.BadRequestAlertException;
import tech.meliora.configserver.service.dto.RudishaConfigDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link tech.meliora.configserver.domain.RudishaConfig}.
 */
@RestController
@RequestMapping("/api")
public class RudishaConfigResource {

    private final Logger log = LoggerFactory.getLogger(RudishaConfigResource.class);

    private static final String ENTITY_NAME = "rudishaConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RudishaConfigService rudishaConfigService;

    public RudishaConfigResource(RudishaConfigService rudishaConfigService) {
        this.rudishaConfigService = rudishaConfigService;
    }

    /**
     * {@code POST  /rudisha-configs} : Create a new rudishaConfig.
     *
     * @param rudishaConfigDTO the rudishaConfigDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rudishaConfigDTO, or with status {@code 400 (Bad Request)} if the rudishaConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rudisha-configs")
    public ResponseEntity<RudishaConfigDTO> createRudishaConfig(@Valid @RequestBody RudishaConfigDTO rudishaConfigDTO) throws URISyntaxException {
        log.debug("REST request to save RudishaConfig : {}", rudishaConfigDTO);
        if (rudishaConfigDTO.getId() != null) {
            throw new BadRequestAlertException("A new rudishaConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RudishaConfigDTO result = rudishaConfigService.save(rudishaConfigDTO);
        return ResponseEntity.created(new URI("/api/rudisha-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rudisha-configs} : Updates an existing rudishaConfig.
     *
     * @param rudishaConfigDTO the rudishaConfigDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rudishaConfigDTO,
     * or with status {@code 400 (Bad Request)} if the rudishaConfigDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rudishaConfigDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rudisha-configs")
    public ResponseEntity<RudishaConfigDTO> updateRudishaConfig(@Valid @RequestBody RudishaConfigDTO rudishaConfigDTO) throws URISyntaxException {
        log.debug("REST request to update RudishaConfig : {}", rudishaConfigDTO);
        if (rudishaConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RudishaConfigDTO result = rudishaConfigService.save(rudishaConfigDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rudishaConfigDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rudisha-configs} : get all the rudishaConfigs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rudishaConfigs in body.
     */
    @GetMapping("/rudisha-configs")
    public List<RudishaConfigDTO> getAllRudishaConfigs() {
        log.debug("REST request to get all RudishaConfigs");
        return rudishaConfigService.findAll();
    }

    /**
     * {@code GET  /rudisha-configs/:id} : get the "id" rudishaConfig.
     *
     * @param id the id of the rudishaConfigDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rudishaConfigDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rudisha-configs/{id}")
    public ResponseEntity<RudishaConfigDTO> getRudishaConfig(@PathVariable Long id) {
        log.debug("REST request to get RudishaConfig : {}", id);
        Optional<RudishaConfigDTO> rudishaConfigDTO = rudishaConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rudishaConfigDTO);
    }

    /**
     * {@code DELETE  /rudisha-configs/:id} : delete the "id" rudishaConfig.
     *
     * @param id the id of the rudishaConfigDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rudisha-configs/{id}")
    public ResponseEntity<Void> deleteRudishaConfig(@PathVariable Long id) {
        log.debug("REST request to delete RudishaConfig : {}", id);
        rudishaConfigService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
