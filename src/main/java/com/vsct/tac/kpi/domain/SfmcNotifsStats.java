package com.vsct.tac.kpi.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A SfmcNotifsStats.
 */
@Entity
@Table(name = "sfmc_stats_quotidiennes")
public class SfmcNotifsStats implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "langue")
    private String langue;

    @NotNull
    @Column(name = "nb_messages", nullable = false)
    private Long nbMessages;

    @Column(name = "event_label")
    private String eventLabel;

    @Column(name = "channel")
    private String channel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SfmcNotifsStats id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public SfmcNotifsStats date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getEventType() {
        return this.eventType;
    }

    public SfmcNotifsStats eventType(String eventType) {
        this.setEventType(eventType);
        return this;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getLangue() {
        return this.langue;
    }

    public SfmcNotifsStats langue(String langue) {
        this.setLangue(langue);
        return this;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public Long getNbMessages() {
        return this.nbMessages;
    }

    public SfmcNotifsStats nbMessages(Long nbMessages) {
        this.setNbMessages(nbMessages);
        return this;
    }

    public void setNbMessages(Long nbMessages) {
        this.nbMessages = nbMessages;
    }

    public String getEventLabel() {
        return this.eventLabel;
    }

    public SfmcNotifsStats eventLabel(String eventLabel) {
        this.setEventLabel(eventLabel);
        return this;
    }

    public void setEventLabel(String eventLabel) {
        this.eventLabel = eventLabel;
    }

    public String getChannel() {
        return this.channel;
    }

    public SfmcNotifsStats channel(String channel) {
        this.setChannel(channel);
        return this;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SfmcNotifsStats)) {
            return false;
        }
        return id != null && id.equals(((SfmcNotifsStats) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SfmcNotifsStats{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", eventType='" + getEventType() + "'" +
            ", langue='" + getLangue() + "'" +
            ", nbMessages=" + getNbMessages() +
            ", eventLabel='" + getEventLabel() + "'" +
            ", channel='" + getChannel() + "'" +
            "}";
    }
}
