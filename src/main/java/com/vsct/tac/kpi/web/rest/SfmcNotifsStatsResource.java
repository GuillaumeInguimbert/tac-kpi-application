package com.vsct.tac.kpi.web.rest;

import com.vsct.tac.kpi.domain.SfmcNotifsStats;
import com.vsct.tac.kpi.repository.SfmcNotifsStatsRepository;
import com.vsct.tac.kpi.service.SfmcNotifsStatsService;
import com.vsct.tac.kpi.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.vsct.tac.kpi.domain.SfmcNotifsStats}.
 */
@RestController
@RequestMapping("/api")
public class SfmcNotifsStatsResource {

    private final Logger log = LoggerFactory.getLogger(SfmcNotifsStatsResource.class);

    private static final String ENTITY_NAME = "sfmcNotifsStats";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SfmcNotifsStatsService sfmcNotifsStatsService;

    private final SfmcNotifsStatsRepository sfmcNotifsStatsRepository;

    public SfmcNotifsStatsResource(SfmcNotifsStatsService sfmcNotifsStatsService, SfmcNotifsStatsRepository sfmcNotifsStatsRepository) {
        this.sfmcNotifsStatsService = sfmcNotifsStatsService;
        this.sfmcNotifsStatsRepository = sfmcNotifsStatsRepository;
    }

    /**
     * {@code POST  /sfmc-notifs-stats} : Create a new sfmcNotifsStats.
     *
     * @param sfmcNotifsStats the sfmcNotifsStats to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sfmcNotifsStats, or with status {@code 400 (Bad Request)} if the sfmcNotifsStats has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sfmc-notifs-stats")
    public ResponseEntity<SfmcNotifsStats> createSfmcNotifsStats(@Valid @RequestBody SfmcNotifsStats sfmcNotifsStats)
        throws URISyntaxException {
        log.debug("REST request to save SfmcNotifsStats : {}", sfmcNotifsStats);
        if (sfmcNotifsStats.getId() != null) {
            throw new BadRequestAlertException("A new sfmcNotifsStats cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SfmcNotifsStats result = sfmcNotifsStatsService.save(sfmcNotifsStats);
        return ResponseEntity
            .created(new URI("/api/sfmc-notifs-stats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sfmc-notifs-stats/:id} : Updates an existing sfmcNotifsStats.
     *
     * @param id the id of the sfmcNotifsStats to save.
     * @param sfmcNotifsStats the sfmcNotifsStats to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sfmcNotifsStats,
     * or with status {@code 400 (Bad Request)} if the sfmcNotifsStats is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sfmcNotifsStats couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sfmc-notifs-stats/{id}")
    public ResponseEntity<SfmcNotifsStats> updateSfmcNotifsStats(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SfmcNotifsStats sfmcNotifsStats
    ) throws URISyntaxException {
        log.debug("REST request to update SfmcNotifsStats : {}, {}", id, sfmcNotifsStats);
        if (sfmcNotifsStats.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sfmcNotifsStats.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sfmcNotifsStatsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SfmcNotifsStats result = sfmcNotifsStatsService.save(sfmcNotifsStats);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sfmcNotifsStats.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sfmc-notifs-stats/:id} : Partial updates given fields of an existing sfmcNotifsStats, field will ignore if it is null
     *
     * @param id the id of the sfmcNotifsStats to save.
     * @param sfmcNotifsStats the sfmcNotifsStats to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sfmcNotifsStats,
     * or with status {@code 400 (Bad Request)} if the sfmcNotifsStats is not valid,
     * or with status {@code 404 (Not Found)} if the sfmcNotifsStats is not found,
     * or with status {@code 500 (Internal Server Error)} if the sfmcNotifsStats couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sfmc-notifs-stats/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SfmcNotifsStats> partialUpdateSfmcNotifsStats(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SfmcNotifsStats sfmcNotifsStats
    ) throws URISyntaxException {
        log.debug("REST request to partial update SfmcNotifsStats partially : {}, {}", id, sfmcNotifsStats);
        if (sfmcNotifsStats.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sfmcNotifsStats.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sfmcNotifsStatsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SfmcNotifsStats> result = sfmcNotifsStatsService.partialUpdate(sfmcNotifsStats);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sfmcNotifsStats.getId().toString())
        );
    }

    /**
     * {@code GET  /sfmc-notifs-stats} : get all the sfmcNotifsStats.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sfmcNotifsStats in body.
     */
    @GetMapping("/sfmc-notifs-stats")
    public ResponseEntity<List<SfmcNotifsStats>> getAllSfmcNotifsStats(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of SfmcNotifsStats");
        Page<SfmcNotifsStats> page = sfmcNotifsStatsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sfmc-notifs-stats/:id} : get the "id" sfmcNotifsStats.
     *
     * @param id the id of the sfmcNotifsStats to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sfmcNotifsStats, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sfmc-notifs-stats/{id}")
    public ResponseEntity<SfmcNotifsStats> getSfmcNotifsStats(@PathVariable Long id) {
        log.debug("REST request to get SfmcNotifsStats : {}", id);
        Optional<SfmcNotifsStats> sfmcNotifsStats = sfmcNotifsStatsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sfmcNotifsStats);
    }

    /**
     * {@code DELETE  /sfmc-notifs-stats/:id} : delete the "id" sfmcNotifsStats.
     *
     * @param id the id of the sfmcNotifsStats to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sfmc-notifs-stats/{id}")
    public ResponseEntity<Void> deleteSfmcNotifsStats(@PathVariable Long id) {
        log.debug("REST request to delete SfmcNotifsStats : {}", id);
        sfmcNotifsStatsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
