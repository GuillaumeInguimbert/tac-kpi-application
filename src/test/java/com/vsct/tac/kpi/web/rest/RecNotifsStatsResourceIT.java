package com.vsct.tac.kpi.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vsct.tac.kpi.IntegrationTest;
import com.vsct.tac.kpi.domain.RecNotifsStats;
import com.vsct.tac.kpi.repository.RecNotifsStatsRepository;
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
 * Integration tests for the {@link RecNotifsStatsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RecNotifsStatsResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PARTNER = "AAAAAAAAAA";
    private static final String UPDATED_PARTNER = "BBBBBBBBBB";

    private static final String DEFAULT_USE_CASE = "AAAAAAAAAA";
    private static final String UPDATED_USE_CASE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_FALLBACK_REASON = "AAAAAAAAAA";
    private static final String UPDATED_FALLBACK_REASON = "BBBBBBBBBB";

    private static final Long DEFAULT_NB_NOTIFICATIONS = 1L;
    private static final Long UPDATED_NB_NOTIFICATIONS = 2L;

    private static final Long DEFAULT_NB_DEVICE_DELIVERED = 1L;
    private static final Long UPDATED_NB_DEVICE_DELIVERED = 2L;

    private static final String ENTITY_API_URL = "/api/rec-notifs-stats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RecNotifsStatsRepository recNotifsStatsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecNotifsStatsMockMvc;

    private RecNotifsStats recNotifsStats;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecNotifsStats createEntity(EntityManager em) {
        RecNotifsStats recNotifsStats = new RecNotifsStats()
            .date(DEFAULT_DATE)
            .partner(DEFAULT_PARTNER)
            .useCase(DEFAULT_USE_CASE)
            .status(DEFAULT_STATUS)
            .fallbackReason(DEFAULT_FALLBACK_REASON)
            .nbNotifications(DEFAULT_NB_NOTIFICATIONS)
            .nbDeviceDelivered(DEFAULT_NB_DEVICE_DELIVERED);
        return recNotifsStats;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecNotifsStats createUpdatedEntity(EntityManager em) {
        RecNotifsStats recNotifsStats = new RecNotifsStats()
            .date(UPDATED_DATE)
            .partner(UPDATED_PARTNER)
            .useCase(UPDATED_USE_CASE)
            .status(UPDATED_STATUS)
            .fallbackReason(UPDATED_FALLBACK_REASON)
            .nbNotifications(UPDATED_NB_NOTIFICATIONS)
            .nbDeviceDelivered(UPDATED_NB_DEVICE_DELIVERED);
        return recNotifsStats;
    }

    @BeforeEach
    public void initTest() {
        recNotifsStats = createEntity(em);
    }

    @Test
    @Transactional
    void createRecNotifsStats() throws Exception {
        int databaseSizeBeforeCreate = recNotifsStatsRepository.findAll().size();
        // Create the RecNotifsStats
        restRecNotifsStatsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recNotifsStats))
            )
            .andExpect(status().isCreated());

        // Validate the RecNotifsStats in the database
        List<RecNotifsStats> recNotifsStatsList = recNotifsStatsRepository.findAll();
        assertThat(recNotifsStatsList).hasSize(databaseSizeBeforeCreate + 1);
        RecNotifsStats testRecNotifsStats = recNotifsStatsList.get(recNotifsStatsList.size() - 1);
        assertThat(testRecNotifsStats.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testRecNotifsStats.getPartner()).isEqualTo(DEFAULT_PARTNER);
        assertThat(testRecNotifsStats.getUseCase()).isEqualTo(DEFAULT_USE_CASE);
        assertThat(testRecNotifsStats.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testRecNotifsStats.getFallbackReason()).isEqualTo(DEFAULT_FALLBACK_REASON);
        assertThat(testRecNotifsStats.getNbNotifications()).isEqualTo(DEFAULT_NB_NOTIFICATIONS);
        assertThat(testRecNotifsStats.getNbDeviceDelivered()).isEqualTo(DEFAULT_NB_DEVICE_DELIVERED);
    }

    @Test
    @Transactional
    void createRecNotifsStatsWithExistingId() throws Exception {
        // Create the RecNotifsStats with an existing ID
        recNotifsStats.setId(1L);

        int databaseSizeBeforeCreate = recNotifsStatsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecNotifsStatsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recNotifsStats))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecNotifsStats in the database
        List<RecNotifsStats> recNotifsStatsList = recNotifsStatsRepository.findAll();
        assertThat(recNotifsStatsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = recNotifsStatsRepository.findAll().size();
        // set the field null
        recNotifsStats.setDate(null);

        // Create the RecNotifsStats, which fails.

        restRecNotifsStatsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recNotifsStats))
            )
            .andExpect(status().isBadRequest());

        List<RecNotifsStats> recNotifsStatsList = recNotifsStatsRepository.findAll();
        assertThat(recNotifsStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPartnerIsRequired() throws Exception {
        int databaseSizeBeforeTest = recNotifsStatsRepository.findAll().size();
        // set the field null
        recNotifsStats.setPartner(null);

        // Create the RecNotifsStats, which fails.

        restRecNotifsStatsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recNotifsStats))
            )
            .andExpect(status().isBadRequest());

        List<RecNotifsStats> recNotifsStatsList = recNotifsStatsRepository.findAll();
        assertThat(recNotifsStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUseCaseIsRequired() throws Exception {
        int databaseSizeBeforeTest = recNotifsStatsRepository.findAll().size();
        // set the field null
        recNotifsStats.setUseCase(null);

        // Create the RecNotifsStats, which fails.

        restRecNotifsStatsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recNotifsStats))
            )
            .andExpect(status().isBadRequest());

        List<RecNotifsStats> recNotifsStatsList = recNotifsStatsRepository.findAll();
        assertThat(recNotifsStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = recNotifsStatsRepository.findAll().size();
        // set the field null
        recNotifsStats.setStatus(null);

        // Create the RecNotifsStats, which fails.

        restRecNotifsStatsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recNotifsStats))
            )
            .andExpect(status().isBadRequest());

        List<RecNotifsStats> recNotifsStatsList = recNotifsStatsRepository.findAll();
        assertThat(recNotifsStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNbNotificationsIsRequired() throws Exception {
        int databaseSizeBeforeTest = recNotifsStatsRepository.findAll().size();
        // set the field null
        recNotifsStats.setNbNotifications(null);

        // Create the RecNotifsStats, which fails.

        restRecNotifsStatsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recNotifsStats))
            )
            .andExpect(status().isBadRequest());

        List<RecNotifsStats> recNotifsStatsList = recNotifsStatsRepository.findAll();
        assertThat(recNotifsStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNbDeviceDeliveredIsRequired() throws Exception {
        int databaseSizeBeforeTest = recNotifsStatsRepository.findAll().size();
        // set the field null
        recNotifsStats.setNbDeviceDelivered(null);

        // Create the RecNotifsStats, which fails.

        restRecNotifsStatsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recNotifsStats))
            )
            .andExpect(status().isBadRequest());

        List<RecNotifsStats> recNotifsStatsList = recNotifsStatsRepository.findAll();
        assertThat(recNotifsStatsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRecNotifsStats() throws Exception {
        // Initialize the database
        recNotifsStatsRepository.saveAndFlush(recNotifsStats);

        // Get all the recNotifsStatsList
        restRecNotifsStatsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recNotifsStats.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].partner").value(hasItem(DEFAULT_PARTNER)))
            .andExpect(jsonPath("$.[*].useCase").value(hasItem(DEFAULT_USE_CASE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].fallbackReason").value(hasItem(DEFAULT_FALLBACK_REASON)))
            .andExpect(jsonPath("$.[*].nbNotifications").value(hasItem(DEFAULT_NB_NOTIFICATIONS.intValue())))
            .andExpect(jsonPath("$.[*].nbDeviceDelivered").value(hasItem(DEFAULT_NB_DEVICE_DELIVERED.intValue())));
    }

    @Test
    @Transactional
    void getRecNotifsStats() throws Exception {
        // Initialize the database
        recNotifsStatsRepository.saveAndFlush(recNotifsStats);

        // Get the recNotifsStats
        restRecNotifsStatsMockMvc
            .perform(get(ENTITY_API_URL_ID, recNotifsStats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recNotifsStats.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.partner").value(DEFAULT_PARTNER))
            .andExpect(jsonPath("$.useCase").value(DEFAULT_USE_CASE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.fallbackReason").value(DEFAULT_FALLBACK_REASON))
            .andExpect(jsonPath("$.nbNotifications").value(DEFAULT_NB_NOTIFICATIONS.intValue()))
            .andExpect(jsonPath("$.nbDeviceDelivered").value(DEFAULT_NB_DEVICE_DELIVERED.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingRecNotifsStats() throws Exception {
        // Get the recNotifsStats
        restRecNotifsStatsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRecNotifsStats() throws Exception {
        // Initialize the database
        recNotifsStatsRepository.saveAndFlush(recNotifsStats);

        int databaseSizeBeforeUpdate = recNotifsStatsRepository.findAll().size();

        // Update the recNotifsStats
        RecNotifsStats updatedRecNotifsStats = recNotifsStatsRepository.findById(recNotifsStats.getId()).get();
        // Disconnect from session so that the updates on updatedRecNotifsStats are not directly saved in db
        em.detach(updatedRecNotifsStats);
        updatedRecNotifsStats
            .date(UPDATED_DATE)
            .partner(UPDATED_PARTNER)
            .useCase(UPDATED_USE_CASE)
            .status(UPDATED_STATUS)
            .fallbackReason(UPDATED_FALLBACK_REASON)
            .nbNotifications(UPDATED_NB_NOTIFICATIONS)
            .nbDeviceDelivered(UPDATED_NB_DEVICE_DELIVERED);

        restRecNotifsStatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRecNotifsStats.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRecNotifsStats))
            )
            .andExpect(status().isOk());

        // Validate the RecNotifsStats in the database
        List<RecNotifsStats> recNotifsStatsList = recNotifsStatsRepository.findAll();
        assertThat(recNotifsStatsList).hasSize(databaseSizeBeforeUpdate);
        RecNotifsStats testRecNotifsStats = recNotifsStatsList.get(recNotifsStatsList.size() - 1);
        assertThat(testRecNotifsStats.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testRecNotifsStats.getPartner()).isEqualTo(UPDATED_PARTNER);
        assertThat(testRecNotifsStats.getUseCase()).isEqualTo(UPDATED_USE_CASE);
        assertThat(testRecNotifsStats.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRecNotifsStats.getFallbackReason()).isEqualTo(UPDATED_FALLBACK_REASON);
        assertThat(testRecNotifsStats.getNbNotifications()).isEqualTo(UPDATED_NB_NOTIFICATIONS);
        assertThat(testRecNotifsStats.getNbDeviceDelivered()).isEqualTo(UPDATED_NB_DEVICE_DELIVERED);
    }

    @Test
    @Transactional
    void putNonExistingRecNotifsStats() throws Exception {
        int databaseSizeBeforeUpdate = recNotifsStatsRepository.findAll().size();
        recNotifsStats.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecNotifsStatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recNotifsStats.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recNotifsStats))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecNotifsStats in the database
        List<RecNotifsStats> recNotifsStatsList = recNotifsStatsRepository.findAll();
        assertThat(recNotifsStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRecNotifsStats() throws Exception {
        int databaseSizeBeforeUpdate = recNotifsStatsRepository.findAll().size();
        recNotifsStats.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecNotifsStatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recNotifsStats))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecNotifsStats in the database
        List<RecNotifsStats> recNotifsStatsList = recNotifsStatsRepository.findAll();
        assertThat(recNotifsStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRecNotifsStats() throws Exception {
        int databaseSizeBeforeUpdate = recNotifsStatsRepository.findAll().size();
        recNotifsStats.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecNotifsStatsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recNotifsStats)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RecNotifsStats in the database
        List<RecNotifsStats> recNotifsStatsList = recNotifsStatsRepository.findAll();
        assertThat(recNotifsStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRecNotifsStatsWithPatch() throws Exception {
        // Initialize the database
        recNotifsStatsRepository.saveAndFlush(recNotifsStats);

        int databaseSizeBeforeUpdate = recNotifsStatsRepository.findAll().size();

        // Update the recNotifsStats using partial update
        RecNotifsStats partialUpdatedRecNotifsStats = new RecNotifsStats();
        partialUpdatedRecNotifsStats.setId(recNotifsStats.getId());

        partialUpdatedRecNotifsStats.partner(UPDATED_PARTNER).status(UPDATED_STATUS).nbDeviceDelivered(UPDATED_NB_DEVICE_DELIVERED);

        restRecNotifsStatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecNotifsStats.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRecNotifsStats))
            )
            .andExpect(status().isOk());

        // Validate the RecNotifsStats in the database
        List<RecNotifsStats> recNotifsStatsList = recNotifsStatsRepository.findAll();
        assertThat(recNotifsStatsList).hasSize(databaseSizeBeforeUpdate);
        RecNotifsStats testRecNotifsStats = recNotifsStatsList.get(recNotifsStatsList.size() - 1);
        assertThat(testRecNotifsStats.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testRecNotifsStats.getPartner()).isEqualTo(UPDATED_PARTNER);
        assertThat(testRecNotifsStats.getUseCase()).isEqualTo(DEFAULT_USE_CASE);
        assertThat(testRecNotifsStats.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRecNotifsStats.getFallbackReason()).isEqualTo(DEFAULT_FALLBACK_REASON);
        assertThat(testRecNotifsStats.getNbNotifications()).isEqualTo(DEFAULT_NB_NOTIFICATIONS);
        assertThat(testRecNotifsStats.getNbDeviceDelivered()).isEqualTo(UPDATED_NB_DEVICE_DELIVERED);
    }

    @Test
    @Transactional
    void fullUpdateRecNotifsStatsWithPatch() throws Exception {
        // Initialize the database
        recNotifsStatsRepository.saveAndFlush(recNotifsStats);

        int databaseSizeBeforeUpdate = recNotifsStatsRepository.findAll().size();

        // Update the recNotifsStats using partial update
        RecNotifsStats partialUpdatedRecNotifsStats = new RecNotifsStats();
        partialUpdatedRecNotifsStats.setId(recNotifsStats.getId());

        partialUpdatedRecNotifsStats
            .date(UPDATED_DATE)
            .partner(UPDATED_PARTNER)
            .useCase(UPDATED_USE_CASE)
            .status(UPDATED_STATUS)
            .fallbackReason(UPDATED_FALLBACK_REASON)
            .nbNotifications(UPDATED_NB_NOTIFICATIONS)
            .nbDeviceDelivered(UPDATED_NB_DEVICE_DELIVERED);

        restRecNotifsStatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecNotifsStats.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRecNotifsStats))
            )
            .andExpect(status().isOk());

        // Validate the RecNotifsStats in the database
        List<RecNotifsStats> recNotifsStatsList = recNotifsStatsRepository.findAll();
        assertThat(recNotifsStatsList).hasSize(databaseSizeBeforeUpdate);
        RecNotifsStats testRecNotifsStats = recNotifsStatsList.get(recNotifsStatsList.size() - 1);
        assertThat(testRecNotifsStats.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testRecNotifsStats.getPartner()).isEqualTo(UPDATED_PARTNER);
        assertThat(testRecNotifsStats.getUseCase()).isEqualTo(UPDATED_USE_CASE);
        assertThat(testRecNotifsStats.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRecNotifsStats.getFallbackReason()).isEqualTo(UPDATED_FALLBACK_REASON);
        assertThat(testRecNotifsStats.getNbNotifications()).isEqualTo(UPDATED_NB_NOTIFICATIONS);
        assertThat(testRecNotifsStats.getNbDeviceDelivered()).isEqualTo(UPDATED_NB_DEVICE_DELIVERED);
    }

    @Test
    @Transactional
    void patchNonExistingRecNotifsStats() throws Exception {
        int databaseSizeBeforeUpdate = recNotifsStatsRepository.findAll().size();
        recNotifsStats.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecNotifsStatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, recNotifsStats.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recNotifsStats))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecNotifsStats in the database
        List<RecNotifsStats> recNotifsStatsList = recNotifsStatsRepository.findAll();
        assertThat(recNotifsStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRecNotifsStats() throws Exception {
        int databaseSizeBeforeUpdate = recNotifsStatsRepository.findAll().size();
        recNotifsStats.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecNotifsStatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recNotifsStats))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecNotifsStats in the database
        List<RecNotifsStats> recNotifsStatsList = recNotifsStatsRepository.findAll();
        assertThat(recNotifsStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRecNotifsStats() throws Exception {
        int databaseSizeBeforeUpdate = recNotifsStatsRepository.findAll().size();
        recNotifsStats.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecNotifsStatsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(recNotifsStats))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RecNotifsStats in the database
        List<RecNotifsStats> recNotifsStatsList = recNotifsStatsRepository.findAll();
        assertThat(recNotifsStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRecNotifsStats() throws Exception {
        // Initialize the database
        recNotifsStatsRepository.saveAndFlush(recNotifsStats);

        int databaseSizeBeforeDelete = recNotifsStatsRepository.findAll().size();

        // Delete the recNotifsStats
        restRecNotifsStatsMockMvc
            .perform(delete(ENTITY_API_URL_ID, recNotifsStats.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RecNotifsStats> recNotifsStatsList = recNotifsStatsRepository.findAll();
        assertThat(recNotifsStatsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
