package com.vsct.tac.kpi.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A RecNotifsStats.
 */
@Entity
@Table(name = "rec_notifs_stats_quotidiennes")
public class RecNotifsStats implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "partner", nullable = false)
    private String partner;

    @NotNull
    @Column(name = "use_case", nullable = false)
    private String useCase;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "fallback_reason")
    private String fallbackReason;

    @NotNull
    @Column(name = "nb_notifications", nullable = false)
    private Long nbNotifications;

    @NotNull
    @Column(name = "nb_device_delivered", nullable = false)
    private Long nbDeviceDelivered;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RecNotifsStats id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public RecNotifsStats date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPartner() {
        return this.partner;
    }

    public RecNotifsStats partner(String partner) {
        this.setPartner(partner);
        return this;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getUseCase() {
        return this.useCase;
    }

    public RecNotifsStats useCase(String useCase) {
        this.setUseCase(useCase);
        return this;
    }

    public void setUseCase(String useCase) {
        this.useCase = useCase;
    }

    public String getStatus() {
        return this.status;
    }

    public RecNotifsStats status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFallbackReason() {
        return this.fallbackReason;
    }

    public RecNotifsStats fallbackReason(String fallbackReason) {
        this.setFallbackReason(fallbackReason);
        return this;
    }

    public void setFallbackReason(String fallbackReason) {
        this.fallbackReason = fallbackReason;
    }

    public Long getNbNotifications() {
        return this.nbNotifications;
    }

    public RecNotifsStats nbNotifications(Long nbNotifications) {
        this.setNbNotifications(nbNotifications);
        return this;
    }

    public void setNbNotifications(Long nbNotifications) {
        this.nbNotifications = nbNotifications;
    }

    public Long getNbDeviceDelivered() {
        return this.nbDeviceDelivered;
    }

    public RecNotifsStats nbDeviceDelivered(Long nbDeviceDelivered) {
        this.setNbDeviceDelivered(nbDeviceDelivered);
        return this;
    }

    public void setNbDeviceDelivered(Long nbDeviceDelivered) {
        this.nbDeviceDelivered = nbDeviceDelivered;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecNotifsStats)) {
            return false;
        }
        return id != null && id.equals(((RecNotifsStats) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecNotifsStats{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", partner='" + getPartner() + "'" +
            ", useCase='" + getUseCase() + "'" +
            ", status='" + getStatus() + "'" +
            ", fallbackReason='" + getFallbackReason() + "'" +
            ", nbNotifications=" + getNbNotifications() +
            ", nbDeviceDelivered=" + getNbDeviceDelivered() +
            "}";
    }
}
