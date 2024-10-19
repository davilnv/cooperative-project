package br.com.davilnv.cooperative.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class VotingSession {
    private UUID id;
    private LocalDateTime openDateTime;
    private LocalDateTime closeDateTime;
    private Agenda agenda;

    public VotingSession() {
    }

    public VotingSession(UUID id, LocalDateTime openDateTime, LocalDateTime closeDateTime, Agenda agenda) {
        this.id = id;
        this.openDateTime = openDateTime;
        this.closeDateTime = closeDateTime;
        this.agenda = agenda;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getOpenDateTime() {
        return openDateTime;
    }

    public void setOpenDateTime(LocalDateTime openDateTime) {
        this.openDateTime = openDateTime;
    }

    public LocalDateTime getCloseDateTime() {
        return closeDateTime;
    }

    public void setCloseDateTime(LocalDateTime closeDateTime) {
        this.closeDateTime = closeDateTime;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }
}
