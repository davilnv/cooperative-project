package br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.mapper;

import br.com.davilnv.cooperative.domain.model.Vote;
import br.com.davilnv.cooperative.domain.model.VotingSession;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.entity.VotingSessionEntity;

import java.util.List;

public class VotingSessionMapper {

    public static VotingSessionEntity toEntity(VotingSession votingSession) {
        VotingSessionEntity votingSessionEntity = new VotingSessionEntity(
                votingSession.getId(),
                votingSession.getOpenDateTime(),
                votingSession.getCloseDateTime()
        );
        return votingSessionEntity;
    }

    public static VotingSession toDomain(VotingSessionEntity votingSessionEntity) {
        if (votingSessionEntity == null) {
            return null;
        }

        VotingSession votingSession = new VotingSession(
                votingSessionEntity.getId(),
                votingSessionEntity.getOpenDateTime(),
                votingSessionEntity.getCloseDateTime()
        );

        if (votingSessionEntity.getVotes() == null || votingSessionEntity.getVotes().isEmpty()) {
            return votingSession;
        }

        if (votingSessionEntity.getVotes() != null && !votingSessionEntity.getVotes().isEmpty()) {
            List<Vote> votes = votingSessionEntity.getVotes()
                    .stream()
                    .map(VotingSessionVoteEntityMapper::toDomain)
                    .toList();
            votingSession.setVotes(votes);
        }

        return votingSession;
    }
}
