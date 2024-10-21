package br.com.davilnv.cooperative.domain.model;

import br.com.davilnv.cooperative.domain.utils.TimeUtils;

import java.time.LocalDateTime;
import java.util.*;

public class VotingSession {
    private UUID id;
    private LocalDateTime openDateTime;
    private LocalDateTime closeDateTime;
    private List<Vote> votes;

    public VotingSession() {
        this.openDateTime = TimeUtils.getDateTimeNow();
        this.closeDateTime = TimeUtils.getDateTimeNowPlusOneMinute();
        this.votes = new ArrayList<>();
    }

    public VotingSession(LocalDateTime closeDateTime) {
        this.openDateTime = TimeUtils.getDateTimeNow();
        this.closeDateTime = closeDateTime != null ? closeDateTime : TimeUtils.getDateTimeNowPlusOneMinute();
        this.votes = new ArrayList<>();
    }

    public VotingSession(UUID id, LocalDateTime openDateTime, LocalDateTime closeDateTime) {
        this.id = id;
        this.openDateTime = openDateTime != null ? openDateTime : TimeUtils.getDateTimeNow();
        this.closeDateTime = closeDateTime != null ? closeDateTime : TimeUtils.getDateTimeNowPlusOneMinute();
        this.votes = new ArrayList<>();
    }

    public void addVote(Vote vote) {
        if (votes == null) {
            votes = new ArrayList<>();
        }

        List<Vote> newVotes = new ArrayList<>(votes);
        newVotes.add(vote);

        setVotes(newVotes);
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

    public List<Vote> getVotes() {
        return votes == null ? new ArrayList<>() : votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        VotingSession that = (VotingSession) obj;

        return id.equals(that.id);

    }
}
