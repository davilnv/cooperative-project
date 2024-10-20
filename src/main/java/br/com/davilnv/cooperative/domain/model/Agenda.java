package br.com.davilnv.cooperative.domain.model;

import br.com.davilnv.cooperative.domain.enums.AgendaStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class Agenda {
    private UUID id;
    private String title;
    private String description;
    private AgendaStatus status;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private VotingSession votingSession;

    public Agenda() {
    }

    public Agenda(UUID id) {
        this.id = id;
        this.status = AgendaStatus.OPEN;
    }

    public Agenda(UUID id, String title, String description, AgendaStatus status, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public boolean canOpenVotingSession() {
        return this.status.equals(AgendaStatus.CREATED) && votingSession == null;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AgendaStatus getStatus() {
        return status;
    }

    public void setStatus(AgendaStatus status) {
        this.status = status;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public VotingSession getVotingSession() {
        return votingSession;
    }

    public void setVotingSession(VotingSession votingSession) {
        this.votingSession = votingSession;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Agenda that = (Agenda) obj;

        return id.equals(that.id);

    }
}
