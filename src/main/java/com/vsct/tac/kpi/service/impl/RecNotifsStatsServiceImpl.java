package com.vsct.tac.kpi.service.impl;

import com.vsct.tac.kpi.domain.RecNotifsStats;
import com.vsct.tac.kpi.repository.RecNotifsStatsRepository;
import com.vsct.tac.kpi.service.RecNotifsStatsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RecNotifsStats}.
 */
@Service
@Transactional
public class RecNotifsStatsServiceImpl implements RecNotifsStatsService {

    private final Logger log = LoggerFactory.getLogger(RecNotifsStatsServiceImpl.class);

    private final RecNotifsStatsRepository recNotifsStatsRepository;

    public RecNotifsStatsServiceImpl(RecNotifsStatsRepository recNotifsStatsRepository) {
        this.recNotifsStatsRepository = recNotifsStatsRepository;
    }

    @Override
    public RecNotifsStats save(RecNotifsStats recNotifsStats) {
        log.debug("Request to save RecNotifsStats : {}", recNotifsStats);
        return recNotifsStatsRepository.save(recNotifsStats);
    }

    @Override
    public Optional<RecNotifsStats> partialUpdate(RecNotifsStats recNotifsStats) {
        log.debug("Request to partially update RecNotifsStats : {}", recNotifsStats);

        return recNotifsStatsRepository
            .findById(recNotifsStats.getId())
            .map(existingRecNotifsStats -> {
                if (recNotifsStats.getDate() != null) {
                    existingRecNotifsStats.setDate(recNotifsStats.getDate());
                }
                if (recNotifsStats.getPartner() != null) {
                    existingRecNotifsStats.setPartner(recNotifsStats.getPartner());
                }
                if (recNotifsStats.getUseCase() != null) {
                    existingRecNotifsStats.setUseCase(recNotifsStats.getUseCase());
                }
                if (recNotifsStats.getStatus() != null) {
                    existingRecNotifsStats.setStatus(recNotifsStats.getStatus());
                }
                if (recNotifsStats.getFallbackReason() != null) {
                    existingRecNotifsStats.setFallbackReason(recNotifsStats.getFallbackReason());
                }
                if (recNotifsStats.getNbNotifications() != null) {
                    existingRecNotifsStats.setNbNotifications(recNotifsStats.getNbNotifications());
                }
                if (recNotifsStats.getNbDeviceDelivered() != null) {
                    existingRecNotifsStats.setNbDeviceDelivered(recNotifsStats.getNbDeviceDelivered());
                }

                return existingRecNotifsStats;
            })
            .map(recNotifsStatsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RecNotifsStats> findAll(Pageable pageable) {
        log.debug("Request to get all RecNotifsStats");
        return recNotifsStatsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RecNotifsStats> findOne(Long id) {
        log.debug("Request to get RecNotifsStats : {}", id);
        return recNotifsStatsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RecNotifsStats : {}", id);
        recNotifsStatsRepository.deleteById(id);
    }
}
