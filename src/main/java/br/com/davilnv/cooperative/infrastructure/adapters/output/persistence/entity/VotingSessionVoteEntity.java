package br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.entity;

import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.enums.MemberVoteEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "VOTING_SESSION_MEMBER")
public class VotingSessionVoteEntity {

    @EmbeddedId
    private VotingSessionVoteid id;

    @Column(name = "MEMBER_VOTE", nullable = false)
    private MemberVoteEntity memberVote;

    @Column(name = "VOTE_DATE_TIME", nullable = false)
    private LocalDateTime voteDateTime;

    public VotingSessionVoteEntity() {
    }

    public VotingSessionVoteEntity(VotingSessionVoteid id, MemberVoteEntity memberVote, LocalDateTime voteDateTime) {
        this.id = id;
        this.memberVote = memberVote;
        this.voteDateTime = voteDateTime;
    }

    public VotingSessionVoteid getId() {
        return id;
    }

    public void setId(VotingSessionVoteid id) {
        this.id = id;
    }

    public MemberVoteEntity getMemberVote() {
        return memberVote;
    }

    public void setMemberVote(MemberVoteEntity memberVote) {
        this.memberVote = memberVote;
    }

    public LocalDateTime getVoteDateTime() {
        return voteDateTime;
    }

    public void setVoteDateTime(LocalDateTime voteDateTime) {
        this.voteDateTime = voteDateTime;
    }
}
