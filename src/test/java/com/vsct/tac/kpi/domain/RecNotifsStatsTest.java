package com.vsct.tac.kpi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.vsct.tac.kpi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RecNotifsStatsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecNotifsStats.class);
        RecNotifsStats recNotifsStats1 = new RecNotifsStats();
        recNotifsStats1.setId(1L);
        RecNotifsStats recNotifsStats2 = new RecNotifsStats();
        recNotifsStats2.setId(recNotifsStats1.getId());
        assertThat(recNotifsStats1).isEqualTo(recNotifsStats2);
        recNotifsStats2.setId(2L);
        assertThat(recNotifsStats1).isNotEqualTo(recNotifsStats2);
        recNotifsStats1.setId(null);
        assertThat(recNotifsStats1).isNotEqualTo(recNotifsStats2);
    }
}
