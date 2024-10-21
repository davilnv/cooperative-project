package br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class VotingSessionVoteid {

    @ManyToOne
    @JoinColumn(name = "VOTING_SESSION_ID")
    private VotingSessionEntity votingSession;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private MemberEntity member;

    public VotingSessionVoteid() {
    }

    public VotingSessionVoteid(VotingSessionEntity votingSession, MemberEntity member) {
        this.votingSession = votingSession;
        this.member = member;
    }

    public VotingSessionEntity getVotingSession() {
        return votingSession;
    }

    public void setVotingSession(VotingSessionEntity votingSession) {
        this.votingSession = votingSession;
    }

    public MemberEntity getMember() {
        return member;
    }

    public void setMember(MemberEntity member) {
        this.member = member;
    }

}
