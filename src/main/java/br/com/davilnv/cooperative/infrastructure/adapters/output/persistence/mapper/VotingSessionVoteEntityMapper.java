package br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.mapper;

import br.com.davilnv.cooperative.domain.enums.MemberVote;
import br.com.davilnv.cooperative.domain.model.Vote;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.entity.VotingSessionVoteEntity;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.entity.VotingSessionVoteid;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.enums.MemberVoteEntity;

public class VotingSessionVoteEntityMapper {
    public static VotingSessionVoteEntity toEntity(Vote vote) {
        return new VotingSessionVoteEntity(
                new VotingSessionVoteid(
                        VotingSessionMapper.toEntity(vote.getAgenda().getVotingSession()),
                        MemberMapper.toEntity(vote.getMember())
                ),
                MemberVoteEntity.valueOf(vote.getMemberVote().name()),
                vote.getVoteDateTime()
        );
    }

    public static Vote toDomain(VotingSessionVoteEntity voteEntity) {
        return voteEntity == null ? null : new Vote(
                null,
                MemberMapper.toDomain(voteEntity.getId().getMember()),
                MemberVote.valueOf(voteEntity.getMemberVote().name()),
                voteEntity.getVoteDateTime()
        );
    }
}
