package br.com.davilnv.cooperative.application.ports.input;

import br.com.davilnv.cooperative.domain.exception.NotFoundAgendaException;
import br.com.davilnv.cooperative.domain.exception.NotFoundVotingSessionException;
import br.com.davilnv.cooperative.domain.model.VotingSession;

import java.util.Optional;
import java.util.UUID;

public interface GetVotingSessionUseCase {

    Optional<VotingSession> getVotingSession(UUID votingSessionId);

    VotingSession getVotingSessionByAgendaId(UUID agendaId) throws NotFoundAgendaException, NotFoundVotingSessionException;

}
