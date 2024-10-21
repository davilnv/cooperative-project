package br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.entity;

import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.enums.AgendaStatusEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "AGENDA")
public class AgendaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "TITLE", nullable = false, length = 60)
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 20)
    private AgendaStatusEntity status;

    @Column(name = "START_DATE_TIME")
    private LocalDateTime startDateTime;

    @Column(name = "END_DATE_TIME")
    private LocalDateTime endDateTime;

    @OneToOne
    @JoinColumn(name = "VOTING_SESSION_ID", referencedColumnName = "ID")
    private VotingSessionEntity votingSession;

    @Column(name = "VOTES_YES")
    private int votesYes;

    @Column(name = "VOTES_NO")
    private int votesNo;

    public AgendaEntity() {
    }

    public AgendaEntity(UUID id, String title, String description, AgendaStatusEntity status, LocalDateTime startDateTime, LocalDateTime endDateTime, VotingSessionEntity votingSession) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.votingSession = votingSession;
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

    public AgendaStatusEntity getStatus() {
        return status;
    }

    public void setStatus(AgendaStatusEntity status) {
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

    public VotingSessionEntity getVotingSession() {
        return votingSession;
    }

    public void setVotingSession(VotingSessionEntity votingSession) {
        this.votingSession = votingSession;
    }

    public int getVotesYes() {
        return votesYes;
    }

    public void setVotesYes(int votesYes) {
        this.votesYes = votesYes;
    }

    public int getVotesNo() {
        return votesNo;
    }

    public void setVotesNo(int votesNo) {
        this.votesNo = votesNo;
    }
}
