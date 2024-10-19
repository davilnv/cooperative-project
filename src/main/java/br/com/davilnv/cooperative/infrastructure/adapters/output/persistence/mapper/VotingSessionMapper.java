package br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.mapper;

import br.com.davilnv.cooperative.domain.model.VotingSession;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.entity.VotingSessionEntity;

public class VotingSessionMapper {

    public static VotingSessionEntity toEntity(VotingSession votingSession) {
        return new VotingSessionEntity(
                votingSession.getId(),
                votingSession.getOpenDateTime(),
                votingSession.getCloseDateTime(),
                AgendaMapper.toEntity(votingSession.getAgenda())
        );
    }

    public static VotingSession toDomain(VotingSessionEntity votingSessionEntity) {
        return votingSessionEntity == null ? null : new VotingSession(
                votingSessionEntity.getId(),
                votingSessionEntity.getOpenDateTime(),
                votingSessionEntity.getCloseDateTime(),
                AgendaMapper.toDomain(votingSessionEntity.getAgenda())
        );
    }
}
