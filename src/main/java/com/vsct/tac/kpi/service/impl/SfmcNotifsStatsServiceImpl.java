package com.vsct.tac.kpi.service.impl;

import com.vsct.tac.kpi.domain.SfmcNotifsStats;
import com.vsct.tac.kpi.repository.SfmcNotifsStatsRepository;
import com.vsct.tac.kpi.service.SfmcNotifsStatsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SfmcNotifsStats}.
 */
@Service
@Transactional
public class SfmcNotifsStatsServiceImpl implements SfmcNotifsStatsService {

    private final Logger log = LoggerFactory.getLogger(SfmcNotifsStatsServiceImpl.class);

    private final SfmcNotifsStatsRepository sfmcNotifsStatsRepository;

    public SfmcNotifsStatsServiceImpl(SfmcNotifsStatsRepository sfmcNotifsStatsRepository) {
        this.sfmcNotifsStatsRepository = sfmcNotifsStatsRepository;
    }

    @Override
    public SfmcNotifsStats save(SfmcNotifsStats sfmcNotifsStats) {
        log.debug("Request to save SfmcNotifsStats : {}", sfmcNotifsStats);
        return sfmcNotifsStatsRepository.save(sfmcNotifsStats);
    }

    @Override
    public Optional<SfmcNotifsStats> partialUpdate(SfmcNotifsStats sfmcNotifsStats) {
        log.debug("Request to partially update SfmcNotifsStats : {}", sfmcNotifsStats);

        return sfmcNotifsStatsRepository
            .findById(sfmcNotifsStats.getId())
            .map(existingSfmcNotifsStats -> {
                if (sfmcNotifsStats.getDate() != null) {
                    existingSfmcNotifsStats.setDate(sfmcNotifsStats.getDate());
                }
                if (sfmcNotifsStats.getEventType() != null) {
                    existingSfmcNotifsStats.setEventType(sfmcNotifsStats.getEventType());
                }
                if (sfmcNotifsStats.getLangue() != null) {
                    existingSfmcNotifsStats.setLangue(sfmcNotifsStats.getLangue());
                }
                if (sfmcNotifsStats.getNbMessages() != null) {
                    existingSfmcNotifsStats.setNbMessages(sfmcNotifsStats.getNbMessages());
                }
                if (sfmcNotifsStats.getEventLabel() != null) {
                    existingSfmcNotifsStats.setEventLabel(sfmcNotifsStats.getEventLabel());
                }
                if (sfmcNotifsStats.getChannel() != null) {
                    existingSfmcNotifsStats.setChannel(sfmcNotifsStats.getChannel());
                }

                return existingSfmcNotifsStats;
            })
            .map(sfmcNotifsStatsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SfmcNotifsStats> findAll(Pageable pageable) {
        log.debug("Request to get all SfmcNotifsStats");
        return sfmcNotifsStatsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SfmcNotifsStats> findOne(Long id) {
        log.debug("Request to get SfmcNotifsStats : {}", id);
        return sfmcNotifsStatsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SfmcNotifsStats : {}", id);
        sfmcNotifsStatsRepository.deleteById(id);
    }
}
