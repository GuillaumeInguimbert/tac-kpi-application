package com.vsct.tac.kpi.service;

import com.vsct.tac.kpi.domain.SfmcNotifsStats;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link SfmcNotifsStats}.
 */
public interface SfmcNotifsStatsService {
    /**
     * Save a sfmcNotifsStats.
     *
     * @param sfmcNotifsStats the entity to save.
     * @return the persisted entity.
     */
    SfmcNotifsStats save(SfmcNotifsStats sfmcNotifsStats);

    /**
     * Partially updates a sfmcNotifsStats.
     *
     * @param sfmcNotifsStats the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SfmcNotifsStats> partialUpdate(SfmcNotifsStats sfmcNotifsStats);

    /**
     * Get all the sfmcNotifsStats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SfmcNotifsStats> findAll(Pageable pageable);

    /**
     * Get the "id" sfmcNotifsStats.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SfmcNotifsStats> findOne(Long id);

    /**
     * Delete the "id" sfmcNotifsStats.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
