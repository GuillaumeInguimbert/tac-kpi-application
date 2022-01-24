package com.vsct.tac.kpi.web.rest;

import com.vsct.tac.kpi.domain.RecNotifsStats;
import com.vsct.tac.kpi.repository.RecNotifsStatsRepository;
import com.vsct.tac.kpi.service.RecNotifsStatsService;
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
 * REST controller for managing {@link com.vsct.tac.kpi.domain.RecNotifsStats}.
 */
@RestController
@RequestMapping("/api")
public class RecNotifsStatsResource {

    private final Logger log = LoggerFactory.getLogger(RecNotifsStatsResource.class);

    private static final String ENTITY_NAME = "recNotifsStats";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecNotifsStatsService recNotifsStatsService;

    private final RecNotifsStatsRepository recNotifsStatsRepository;

    public RecNotifsStatsResource(RecNotifsStatsService recNotifsStatsService, RecNotifsStatsRepository recNotifsStatsRepository) {
        this.recNotifsStatsService = recNotifsStatsService;
        this.recNotifsStatsRepository = recNotifsStatsRepository;
    }

    /**
     * {@code POST  /rec-notifs-stats} : Create a new recNotifsStats.
     *
     * @param recNotifsStats the recNotifsStats to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recNotifsStats, or with status {@code 400 (Bad Request)} if the recNotifsStats has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rec-notifs-stats")
    public ResponseEntity<RecNotifsStats> createRecNotifsStats(@Valid @RequestBody RecNotifsStats recNotifsStats)
        throws URISyntaxException {
        log.debug("REST request to save RecNotifsStats : {}", recNotifsStats);
        if (recNotifsStats.getId() != null) {
            throw new BadRequestAlertException("A new recNotifsStats cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecNotifsStats result = recNotifsStatsService.save(recNotifsStats);
        return ResponseEntity
            .created(new URI("/api/rec-notifs-stats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rec-notifs-stats/:id} : Updates an existing recNotifsStats.
     *
     * @param id the id of the recNotifsStats to save.
     * @param recNotifsStats the recNotifsStats to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recNotifsStats,
     * or with status {@code 400 (Bad Request)} if the recNotifsStats is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recNotifsStats couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rec-notifs-stats/{id}")
    public ResponseEntity<RecNotifsStats> updateRecNotifsStats(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RecNotifsStats recNotifsStats
    ) throws URISyntaxException {
        log.debug("REST request to update RecNotifsStats : {}, {}", id, recNotifsStats);
        if (recNotifsStats.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recNotifsStats.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recNotifsStatsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RecNotifsStats result = recNotifsStatsService.save(recNotifsStats);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recNotifsStats.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rec-notifs-stats/:id} : Partial updates given fields of an existing recNotifsStats, field will ignore if it is null
     *
     * @param id the id of the recNotifsStats to save.
     * @param recNotifsStats the recNotifsStats to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recNotifsStats,
     * or with status {@code 400 (Bad Request)} if the recNotifsStats is not valid,
     * or with status {@code 404 (Not Found)} if the recNotifsStats is not found,
     * or with status {@code 500 (Internal Server Error)} if the recNotifsStats couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rec-notifs-stats/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RecNotifsStats> partialUpdateRecNotifsStats(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RecNotifsStats recNotifsStats
    ) throws URISyntaxException {
        log.debug("REST request to partial update RecNotifsStats partially : {}, {}", id, recNotifsStats);
        if (recNotifsStats.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recNotifsStats.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recNotifsStatsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RecNotifsStats> result = recNotifsStatsService.partialUpdate(recNotifsStats);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recNotifsStats.getId().toString())
        );
    }

    /**
     * {@code GET  /rec-notifs-stats} : get all the recNotifsStats.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recNotifsStats in body.
     */
    @GetMapping("/rec-notifs-stats")
    public ResponseEntity<List<RecNotifsStats>> getAllRecNotifsStats(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of RecNotifsStats");
        Page<RecNotifsStats> page = recNotifsStatsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rec-notifs-stats/:id} : get the "id" recNotifsStats.
     *
     * @param id the id of the recNotifsStats to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recNotifsStats, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rec-notifs-stats/{id}")
    public ResponseEntity<RecNotifsStats> getRecNotifsStats(@PathVariable Long id) {
        log.debug("REST request to get RecNotifsStats : {}", id);
        Optional<RecNotifsStats> recNotifsStats = recNotifsStatsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recNotifsStats);
    }

    /**
     * {@code DELETE  /rec-notifs-stats/:id} : delete the "id" recNotifsStats.
     *
     * @param id the id of the recNotifsStats to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rec-notifs-stats/{id}")
    public ResponseEntity<Void> deleteRecNotifsStats(@PathVariable Long id) {
        log.debug("REST request to delete RecNotifsStats : {}", id);
        recNotifsStatsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
