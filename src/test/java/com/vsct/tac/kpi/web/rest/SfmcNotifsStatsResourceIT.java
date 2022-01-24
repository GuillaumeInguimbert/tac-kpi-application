package com.vsct.tac.kpi.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vsct.tac.kpi.IntegrationTest;
import com.vsct.tac.kpi.domain.SfmcNotifsStats;
import com.vsct.tac.kpi.repository.SfmcNotifsStatsRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SfmcNotifsStatsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SfmcNotifsStatsResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EVENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUE = "BBBBBBBBBB";

    private static final Long DEFAULT_NB_MESSAGES = 1L;
    private static final Long UPDATED_NB_MESSAGES = 2L;

    private static final String DEFAULT_EVENT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_CHANNEL = "AAAAAAAAAA";
    private static final String UPDATED_CHANNEL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sfmc-notifs-stats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SfmcNotifsStatsRepository sfmcNotifsStatsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSfmcNotifsStatsMockMvc;

    private SfmcNotifsStats sfmcNotifsStats;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SfmcNotifsStats createEntity(EntityManager em) {
        SfmcNotifsStats sfmcNotifsStats = new SfmcNotifsStats()
            .date(DEFAULT_DATE)
            .eventType(DEFAULT_EVENT_TYPE)
            .langue(DEFAULT_LANGUE)
            .nbMessages(DEFAULT_NB_MESSAGES)
            .eventLabel(DEFAULT_EVENT_LABEL)
            .channel(DEFAULT_CHANNEL);
        return sfmcNotifsStats;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SfmcNotifsStats createUpdatedEntity(EntityManager em) {
        SfmcNotifsStats sfmcNotifsStats = new SfmcNotifsStats()
            .date(UPDATED_DATE)
            .eventType(UPDATED_EVENT_TYPE)
            .langue(UPDATED_LANGUE)
            .nbMessages(UPDATED_NB_MESSAGES)
            .eventLabel(UPDATED_EVENT_LABEL)
            .channel(UPDATED_CHANNEL);
        return sfmcNotifsStats;
    }

    @BeforeEach
    public void initTest() {
        sfmcNotifsStats = createEntity(em);
    }

    @Test
    @Transactional
    void createSfmcNotifsStats() throws Exception {
        int databaseSizeBeforeCreate = sfmcNotifsStatsRepository.findAll().size();
        // Create the SfmcNotifsStats
        restSfmcNotifsStatsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sfmcNotifsStats))
            )
            .andExpect(status().isCreated());

        // Validate the SfmcNotifsStats in the database
        List<SfmcNotifsStats> sfmcNotifsStatsList = sfmcNotifsStatsRepository.findAll();
        assertThat(sfmcNotifsStatsList).hasSize(databaseSizeBeforeCreate + 1);
        SfmcNotifsStats testSfmcNotifsStats = sfmcNotifsStatsList.get(sfmcNotifsStatsList.size() - 1);
        assertThat(testSfmcNotifsStats.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testSfmcNotifsStats.getEventType()).isEqualTo(DEFAULT_EVENT_TYPE);
        assertThat(testSfmcNotifsStats.getLangue()).isEqualTo(DEFAULT_LANGUE);
        assertThat(testSfmcNotifsStats.getNbMessages()).isEqualTo(DEFAULT_NB_MESSAGES);
        assertThat(testSfmcNotifsStats.getEventLabel()).isEqualTo(DEFAULT_EVENT_LABEL);
        assertThat(testSfmcNotifsStats.getChannel()).isEqualTo(DEFAULT_CHANNEL);
    }

    @Test
    @Transactional
    void createSfmcNotifsStatsWithExistingId() throws Exception {
        // Create the SfmcNotifsStats with an existing ID
        sfmcNotifsStats.setId(1L);

        int databaseSizeBeforeCreate = sfmcNotifsStatsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSfmcNotifsStatsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sfmcNotifsStats))
            )
            .andExpect(status().isBadRequest());

        // Validate the SfmcNotifsStats in the database
        List<SfmcNotifsStats> sfmcNotifsStatsList = sfmcNotifsStatsRepository.findAll();
        assertThat(sfmcNotifsStatsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = sfmcNotifsStatsRepository.findAll().size();
        // set the field null
        sfmcNotifsStats.setDate(null);

        // Create the SfmcNotifsStats, which fails.

        restSfmcNotifsStatsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sfmcNotifsStats))
            )
            .andExpect(status().isBadRequest());

        List<SfmcNotifsStats> sfmcNotifsStatsList = sfmcNotifsStatsRepository.findAll();
        assertThat(sfmcNotifsStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNbMessagesIsRequired() throws Exception {
        int databaseSizeBeforeTest = sfmcNotifsStatsRepository.findAll().size();
        // set the field null
        sfmcNotifsStats.setNbMessages(null);

        // Create the SfmcNotifsStats, which fails.

        restSfmcNotifsStatsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sfmcNotifsStats))
            )
            .andExpect(status().isBadRequest());

        List<SfmcNotifsStats> sfmcNotifsStatsList = sfmcNotifsStatsRepository.findAll();
        assertThat(sfmcNotifsStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSfmcNotifsStats() throws Exception {
        // Initialize the database
        sfmcNotifsStatsRepository.saveAndFlush(sfmcNotifsStats);

        // Get all the sfmcNotifsStatsList
        restSfmcNotifsStatsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sfmcNotifsStats.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].eventType").value(hasItem(DEFAULT_EVENT_TYPE)))
            .andExpect(jsonPath("$.[*].langue").value(hasItem(DEFAULT_LANGUE)))
            .andExpect(jsonPath("$.[*].nbMessages").value(hasItem(DEFAULT_NB_MESSAGES.intValue())))
            .andExpect(jsonPath("$.[*].eventLabel").value(hasItem(DEFAULT_EVENT_LABEL)))
            .andExpect(jsonPath("$.[*].channel").value(hasItem(DEFAULT_CHANNEL)));
    }

    @Test
    @Transactional
    void getSfmcNotifsStats() throws Exception {
        // Initialize the database
        sfmcNotifsStatsRepository.saveAndFlush(sfmcNotifsStats);

        // Get the sfmcNotifsStats
        restSfmcNotifsStatsMockMvc
            .perform(get(ENTITY_API_URL_ID, sfmcNotifsStats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sfmcNotifsStats.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.eventType").value(DEFAULT_EVENT_TYPE))
            .andExpect(jsonPath("$.langue").value(DEFAULT_LANGUE))
            .andExpect(jsonPath("$.nbMessages").value(DEFAULT_NB_MESSAGES.intValue()))
            .andExpect(jsonPath("$.eventLabel").value(DEFAULT_EVENT_LABEL))
            .andExpect(jsonPath("$.channel").value(DEFAULT_CHANNEL));
    }

    @Test
    @Transactional
    void getNonExistingSfmcNotifsStats() throws Exception {
        // Get the sfmcNotifsStats
        restSfmcNotifsStatsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSfmcNotifsStats() throws Exception {
        // Initialize the database
        sfmcNotifsStatsRepository.saveAndFlush(sfmcNotifsStats);

        int databaseSizeBeforeUpdate = sfmcNotifsStatsRepository.findAll().size();

        // Update the sfmcNotifsStats
        SfmcNotifsStats updatedSfmcNotifsStats = sfmcNotifsStatsRepository.findById(sfmcNotifsStats.getId()).get();
        // Disconnect from session so that the updates on updatedSfmcNotifsStats are not directly saved in db
        em.detach(updatedSfmcNotifsStats);
        updatedSfmcNotifsStats
            .date(UPDATED_DATE)
            .eventType(UPDATED_EVENT_TYPE)
            .langue(UPDATED_LANGUE)
            .nbMessages(UPDATED_NB_MESSAGES)
            .eventLabel(UPDATED_EVENT_LABEL)
            .channel(UPDATED_CHANNEL);

        restSfmcNotifsStatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSfmcNotifsStats.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSfmcNotifsStats))
            )
            .andExpect(status().isOk());

        // Validate the SfmcNotifsStats in the database
        List<SfmcNotifsStats> sfmcNotifsStatsList = sfmcNotifsStatsRepository.findAll();
        assertThat(sfmcNotifsStatsList).hasSize(databaseSizeBeforeUpdate);
        SfmcNotifsStats testSfmcNotifsStats = sfmcNotifsStatsList.get(sfmcNotifsStatsList.size() - 1);
        assertThat(testSfmcNotifsStats.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSfmcNotifsStats.getEventType()).isEqualTo(UPDATED_EVENT_TYPE);
        assertThat(testSfmcNotifsStats.getLangue()).isEqualTo(UPDATED_LANGUE);
        assertThat(testSfmcNotifsStats.getNbMessages()).isEqualTo(UPDATED_NB_MESSAGES);
        assertThat(testSfmcNotifsStats.getEventLabel()).isEqualTo(UPDATED_EVENT_LABEL);
        assertThat(testSfmcNotifsStats.getChannel()).isEqualTo(UPDATED_CHANNEL);
    }

    @Test
    @Transactional
    void putNonExistingSfmcNotifsStats() throws Exception {
        int databaseSizeBeforeUpdate = sfmcNotifsStatsRepository.findAll().size();
        sfmcNotifsStats.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSfmcNotifsStatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sfmcNotifsStats.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sfmcNotifsStats))
            )
            .andExpect(status().isBadRequest());

        // Validate the SfmcNotifsStats in the database
        List<SfmcNotifsStats> sfmcNotifsStatsList = sfmcNotifsStatsRepository.findAll();
        assertThat(sfmcNotifsStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSfmcNotifsStats() throws Exception {
        int databaseSizeBeforeUpdate = sfmcNotifsStatsRepository.findAll().size();
        sfmcNotifsStats.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSfmcNotifsStatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sfmcNotifsStats))
            )
            .andExpect(status().isBadRequest());

        // Validate the SfmcNotifsStats in the database
        List<SfmcNotifsStats> sfmcNotifsStatsList = sfmcNotifsStatsRepository.findAll();
        assertThat(sfmcNotifsStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSfmcNotifsStats() throws Exception {
        int databaseSizeBeforeUpdate = sfmcNotifsStatsRepository.findAll().size();
        sfmcNotifsStats.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSfmcNotifsStatsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sfmcNotifsStats))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SfmcNotifsStats in the database
        List<SfmcNotifsStats> sfmcNotifsStatsList = sfmcNotifsStatsRepository.findAll();
        assertThat(sfmcNotifsStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSfmcNotifsStatsWithPatch() throws Exception {
        // Initialize the database
        sfmcNotifsStatsRepository.saveAndFlush(sfmcNotifsStats);

        int databaseSizeBeforeUpdate = sfmcNotifsStatsRepository.findAll().size();

        // Update the sfmcNotifsStats using partial update
        SfmcNotifsStats partialUpdatedSfmcNotifsStats = new SfmcNotifsStats();
        partialUpdatedSfmcNotifsStats.setId(sfmcNotifsStats.getId());

        partialUpdatedSfmcNotifsStats.date(UPDATED_DATE).eventType(UPDATED_EVENT_TYPE).channel(UPDATED_CHANNEL);

        restSfmcNotifsStatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSfmcNotifsStats.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSfmcNotifsStats))
            )
            .andExpect(status().isOk());

        // Validate the SfmcNotifsStats in the database
        List<SfmcNotifsStats> sfmcNotifsStatsList = sfmcNotifsStatsRepository.findAll();
        assertThat(sfmcNotifsStatsList).hasSize(databaseSizeBeforeUpdate);
        SfmcNotifsStats testSfmcNotifsStats = sfmcNotifsStatsList.get(sfmcNotifsStatsList.size() - 1);
        assertThat(testSfmcNotifsStats.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSfmcNotifsStats.getEventType()).isEqualTo(UPDATED_EVENT_TYPE);
        assertThat(testSfmcNotifsStats.getLangue()).isEqualTo(DEFAULT_LANGUE);
        assertThat(testSfmcNotifsStats.getNbMessages()).isEqualTo(DEFAULT_NB_MESSAGES);
        assertThat(testSfmcNotifsStats.getEventLabel()).isEqualTo(DEFAULT_EVENT_LABEL);
        assertThat(testSfmcNotifsStats.getChannel()).isEqualTo(UPDATED_CHANNEL);
    }

    @Test
    @Transactional
    void fullUpdateSfmcNotifsStatsWithPatch() throws Exception {
        // Initialize the database
        sfmcNotifsStatsRepository.saveAndFlush(sfmcNotifsStats);

        int databaseSizeBeforeUpdate = sfmcNotifsStatsRepository.findAll().size();

        // Update the sfmcNotifsStats using partial update
        SfmcNotifsStats partialUpdatedSfmcNotifsStats = new SfmcNotifsStats();
        partialUpdatedSfmcNotifsStats.setId(sfmcNotifsStats.getId());

        partialUpdatedSfmcNotifsStats
            .date(UPDATED_DATE)
            .eventType(UPDATED_EVENT_TYPE)
            .langue(UPDATED_LANGUE)
            .nbMessages(UPDATED_NB_MESSAGES)
            .eventLabel(UPDATED_EVENT_LABEL)
            .channel(UPDATED_CHANNEL);

        restSfmcNotifsStatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSfmcNotifsStats.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSfmcNotifsStats))
            )
            .andExpect(status().isOk());

        // Validate the SfmcNotifsStats in the database
        List<SfmcNotifsStats> sfmcNotifsStatsList = sfmcNotifsStatsRepository.findAll();
        assertThat(sfmcNotifsStatsList).hasSize(databaseSizeBeforeUpdate);
        SfmcNotifsStats testSfmcNotifsStats = sfmcNotifsStatsList.get(sfmcNotifsStatsList.size() - 1);
        assertThat(testSfmcNotifsStats.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSfmcNotifsStats.getEventType()).isEqualTo(UPDATED_EVENT_TYPE);
        assertThat(testSfmcNotifsStats.getLangue()).isEqualTo(UPDATED_LANGUE);
        assertThat(testSfmcNotifsStats.getNbMessages()).isEqualTo(UPDATED_NB_MESSAGES);
        assertThat(testSfmcNotifsStats.getEventLabel()).isEqualTo(UPDATED_EVENT_LABEL);
        assertThat(testSfmcNotifsStats.getChannel()).isEqualTo(UPDATED_CHANNEL);
    }

    @Test
    @Transactional
    void patchNonExistingSfmcNotifsStats() throws Exception {
        int databaseSizeBeforeUpdate = sfmcNotifsStatsRepository.findAll().size();
        sfmcNotifsStats.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSfmcNotifsStatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sfmcNotifsStats.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sfmcNotifsStats))
            )
            .andExpect(status().isBadRequest());

        // Validate the SfmcNotifsStats in the database
        List<SfmcNotifsStats> sfmcNotifsStatsList = sfmcNotifsStatsRepository.findAll();
        assertThat(sfmcNotifsStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSfmcNotifsStats() throws Exception {
        int databaseSizeBeforeUpdate = sfmcNotifsStatsRepository.findAll().size();
        sfmcNotifsStats.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSfmcNotifsStatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sfmcNotifsStats))
            )
            .andExpect(status().isBadRequest());

        // Validate the SfmcNotifsStats in the database
        List<SfmcNotifsStats> sfmcNotifsStatsList = sfmcNotifsStatsRepository.findAll();
        assertThat(sfmcNotifsStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSfmcNotifsStats() throws Exception {
        int databaseSizeBeforeUpdate = sfmcNotifsStatsRepository.findAll().size();
        sfmcNotifsStats.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSfmcNotifsStatsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sfmcNotifsStats))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SfmcNotifsStats in the database
        List<SfmcNotifsStats> sfmcNotifsStatsList = sfmcNotifsStatsRepository.findAll();
        assertThat(sfmcNotifsStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSfmcNotifsStats() throws Exception {
        // Initialize the database
        sfmcNotifsStatsRepository.saveAndFlush(sfmcNotifsStats);

        int databaseSizeBeforeDelete = sfmcNotifsStatsRepository.findAll().size();

        // Delete the sfmcNotifsStats
        restSfmcNotifsStatsMockMvc
            .perform(delete(ENTITY_API_URL_ID, sfmcNotifsStats.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SfmcNotifsStats> sfmcNotifsStatsList = sfmcNotifsStatsRepository.findAll();
        assertThat(sfmcNotifsStatsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
