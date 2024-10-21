package br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "VOTING_SESSION")
public class VotingSessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "OPEN_DATE_TIME", nullable = false)
    private LocalDateTime openDateTime;

    @Column(name = "CLOSE_DATE_TIME")
    private LocalDateTime closeDateTime;

    @OneToMany(mappedBy = "id.votingSession", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VotingSessionVoteEntity> votes = new ArrayList<>();

    public VotingSessionEntity(
    ) {
    }

    public VotingSessionEntity(UUID id, LocalDateTime openDateTime, LocalDateTime closeDateTime) {
        this.id = id;
        this.openDateTime = openDateTime;
        this.closeDateTime = closeDateTime;
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

    public List<VotingSessionVoteEntity> getVotes() {
        return votes;
    }

    public void setVotes(List<VotingSessionVoteEntity> votes) {
        this.votes = votes;
    }
}
