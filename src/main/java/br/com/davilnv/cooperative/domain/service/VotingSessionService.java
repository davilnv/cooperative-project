package br.com.davilnv.cooperative.domain.service;

import br.com.davilnv.cooperative.application.ports.input.CreateVotingSessionUseCase;
import br.com.davilnv.cooperative.application.ports.input.GetVotingSessionUseCase;
import br.com.davilnv.cooperative.application.ports.output.AgendaOutputPort;
import br.com.davilnv.cooperative.application.ports.output.VotingSessionOutputPort;
import br.com.davilnv.cooperative.domain.enums.AgendaStatus;
import br.com.davilnv.cooperative.domain.exception.NotFoundAgendaException;
import br.com.davilnv.cooperative.domain.exception.NotFoundVotingSessionException;
import br.com.davilnv.cooperative.domain.exception.RequiredAgendaException;
import br.com.davilnv.cooperative.domain.exception.TheresAlreadyOpenVotingSessionException;
import br.com.davilnv.cooperative.domain.model.Agenda;
import br.com.davilnv.cooperative.domain.model.VotingSession;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VotingSessionService implements CreateVotingSessionUseCase, GetVotingSessionUseCase {

    private final VotingSessionOutputPort votingSessionOutputPort;
    private final AgendaOutputPort agendaOutputPort;

    public VotingSessionService(VotingSessionOutputPort votingSessionOutputPort, AgendaOutputPort agendaOutputPort) {
        this.votingSessionOutputPort = votingSessionOutputPort;
        this.agendaOutputPort = agendaOutputPort;
    }

    @Override
    public VotingSession createVotingSession(VotingSession votingSession) throws RequiredAgendaException, NotFoundAgendaException, TheresAlreadyOpenVotingSessionException {
        if (votingSession.getAgenda() == null || votingSession.getAgenda().getId() == null) {
            throw new RequiredAgendaException("Pauta é obrigatória para criar uma sessão de votação");
        }

        Agenda agenda = agendaOutputPort.findById(votingSession.getAgenda().getId());
        if (agenda.getVotingSession() != null) {
            throw new TheresAlreadyOpenVotingSessionException("Já existe uma sessão de votação para a pauta de ID: " + votingSession.getAgenda().getId());
        }
        votingSession.setAgenda(agenda);

        VotingSession votingSessionCreated = votingSessionOutputPort.save(votingSession);
        agenda.setVotingSession(votingSessionCreated);
        agenda.setStatus(AgendaStatus.OPEN);

        votingSessionCreated.setAgenda(agendaOutputPort.save(agenda));
        return votingSessionCreated;

    }

    @Override
    public VotingSession getVotingSession(UUID votingSessionId) throws NotFoundVotingSessionException {
        return votingSessionOutputPort.findById(votingSessionId);
    }

    @Override
    public VotingSession getVotingSessionByAgendaId(UUID agendaId) throws NotFoundAgendaException, NotFoundVotingSessionException {
        Agenda agenda = agendaOutputPort.findById(agendaId);
        if (agenda.getVotingSession() != null) {
            return agenda.getVotingSession();
        }
        throw new NotFoundVotingSessionException("Sessão de votação não aberta para a pauta de ID: " + agendaId);
    }
}