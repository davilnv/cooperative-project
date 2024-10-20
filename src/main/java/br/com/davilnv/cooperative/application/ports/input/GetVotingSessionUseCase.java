package br.com.davilnv.cooperative.application.ports.input;

import br.com.davilnv.cooperative.domain.exception.NotFoundAgendaException;
import br.com.davilnv.cooperative.domain.exception.NotFoundVotingSessionException;
import br.com.davilnv.cooperative.domain.model.VotingSession;

import java.util.UUID;

public interface GetVotingSessionUseCase {

    VotingSession getVotingSession(UUID votingSessionId) throws NotFoundVotingSessionException;

    VotingSession getVotingSessionByAgendaId(UUID agendaId) throws NotFoundAgendaException, NotFoundVotingSessionException;

}
