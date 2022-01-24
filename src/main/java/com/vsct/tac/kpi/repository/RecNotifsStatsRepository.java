package com.vsct.tac.kpi.repository;

import com.vsct.tac.kpi.domain.RecNotifsStats;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RecNotifsStats entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecNotifsStatsRepository extends JpaRepository<RecNotifsStats, Long> {}
