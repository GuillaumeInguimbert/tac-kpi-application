package com.vsct.tac.kpi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.vsct.tac.kpi.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SfmcNotifsStatsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SfmcNotifsStats.class);
        SfmcNotifsStats sfmcNotifsStats1 = new SfmcNotifsStats();
        sfmcNotifsStats1.setId(1L);
        SfmcNotifsStats sfmcNotifsStats2 = new SfmcNotifsStats();
        sfmcNotifsStats2.setId(sfmcNotifsStats1.getId());
        assertThat(sfmcNotifsStats1).isEqualTo(sfmcNotifsStats2);
        sfmcNotifsStats2.setId(2L);
        assertThat(sfmcNotifsStats1).isNotEqualTo(sfmcNotifsStats2);
        sfmcNotifsStats1.setId(null);
        assertThat(sfmcNotifsStats1).isNotEqualTo(sfmcNotifsStats2);
    }
}
