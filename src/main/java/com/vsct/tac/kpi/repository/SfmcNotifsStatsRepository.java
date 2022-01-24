package com.vsct.tac.kpi.repository;

import com.vsct.tac.kpi.domain.SfmcNotifsStats;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SfmcNotifsStats entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SfmcNotifsStatsRepository extends JpaRepository<SfmcNotifsStats, Long> {}
