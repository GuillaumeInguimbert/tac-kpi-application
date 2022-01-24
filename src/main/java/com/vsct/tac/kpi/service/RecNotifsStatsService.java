package com.vsct.tac.kpi.service;

import com.vsct.tac.kpi.domain.RecNotifsStats;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link RecNotifsStats}.
 */
public interface RecNotifsStatsService {
    /**
     * Save a recNotifsStats.
     *
     * @param recNotifsStats the entity to save.
     * @return the persisted entity.
     */
    RecNotifsStats save(RecNotifsStats recNotifsStats);

    /**
     * Partially updates a recNotifsStats.
     *
     * @param recNotifsStats the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RecNotifsStats> partialUpdate(RecNotifsStats recNotifsStats);

    /**
     * Get all the recNotifsStats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RecNotifsStats> findAll(Pageable pageable);

    /**
     * Get the "id" recNotifsStats.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RecNotifsStats> findOne(Long id);

    /**
     * Delete the "id" recNotifsStats.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
