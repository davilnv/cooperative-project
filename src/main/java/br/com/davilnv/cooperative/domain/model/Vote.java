package br.com.davilnv.cooperative.domain.model;

import br.com.davilnv.cooperative.domain.enums.MemberVote;

import java.time.LocalDateTime;

public class Vote {
    private Agenda agenda;
    private Member member;
    private MemberVote memberVote;
    private LocalDateTime voteDateTime;

    public Vote() {
    }

    public Vote(Agenda agenda, Member member, MemberVote memberVote, LocalDateTime voteDateTime) {
        this.agenda = agenda;
        this.member = member;
        this.memberVote = memberVote;
        this.voteDateTime = voteDateTime;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public MemberVote getMemberVote() {
        return memberVote;
    }

    public void setMemberVote(MemberVote memberVote) {
        this.memberVote = memberVote;
    }

    public LocalDateTime getVoteDateTime() {
        return voteDateTime;
    }

    public void setVoteDateTime(LocalDateTime voteDateTime) {
        this.voteDateTime = voteDateTime;
    }
}
