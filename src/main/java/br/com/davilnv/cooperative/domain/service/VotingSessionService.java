package br.com.davilnv.cooperative.domain.service;

import br.com.davilnv.cooperative.application.ports.input.CreateVotingSessionUseCase;
import br.com.davilnv.cooperative.application.ports.input.GetVotingSessionUseCase;
import br.com.davilnv.cooperative.application.ports.input.PerformVoteUseCase;
import br.com.davilnv.cooperative.application.ports.output.AgendaOutputPort;
import br.com.davilnv.cooperative.application.ports.output.MemberOutputPort;
import br.com.davilnv.cooperative.application.ports.output.VotingSessionOutputPort;
import br.com.davilnv.cooperative.domain.enums.AgendaStatus;
import br.com.davilnv.cooperative.domain.exception.*;
import br.com.davilnv.cooperative.domain.model.Agenda;
import br.com.davilnv.cooperative.domain.model.Member;
import br.com.davilnv.cooperative.domain.model.Vote;
import br.com.davilnv.cooperative.domain.model.VotingSession;
import br.com.davilnv.cooperative.domain.utils.TimeUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VotingSessionService implements CreateVotingSessionUseCase, GetVotingSessionUseCase, PerformVoteUseCase {

    private final VotingSessionOutputPort votingSessionOutputPort;
    private final AgendaOutputPort agendaOutputPort;
    private final MemberOutputPort memberOutputPort;

    public VotingSessionService(VotingSessionOutputPort votingSessionOutputPort, AgendaOutputPort agendaOutputPort, MemberOutputPort memberOutputPort) {
        this.votingSessionOutputPort = votingSessionOutputPort;
        this.agendaOutputPort = agendaOutputPort;
        this.memberOutputPort = memberOutputPort;
    }

    @Override
    public VotingSession createVotingSession(UUID agendaId, LocalDateTime closeDateTime)
            throws RequiredAgendaException, NotFoundAgendaException, TheresAlreadyOpenVotingSessionException {
        if (agendaId == null) {
            throw new RequiredAgendaException("Pauta é obrigatória para criar uma sessão de votação");
        }

        Agenda agenda = agendaOutputPort.findById(agendaId);
        if (agenda == null) {
            throw new NotFoundAgendaException("Pauta não encontrada para o ID: " + agendaId);
        }

        if (agenda.canOpenVotingSession()) {
            VotingSession votingSessionCreated = votingSessionOutputPort.save(new VotingSession(closeDateTime));

            agenda.setVotingSession(votingSessionCreated);
            agenda.setStatus(AgendaStatus.OPEN);
            agendaOutputPort.save(agenda);

            return votingSessionCreated;
        }

        throw new TheresAlreadyOpenVotingSessionException("Já existe uma sessão de votação para a pauta de ID: " + agendaId);
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

    @Override
    public Vote performVote(Vote vote)
            throws NotFoundAgendaException, NotFoundVotingSessionException, NotFoundMemberException, MemberAlreadyVotedException, InvalidInformationVoteException {
        // Verifica se o membro e a pauta foram informados
        if (vote.getMember() != null && vote.getAgenda() != null) {
            // Verifica se existe uma sessão para aquela pauta informada
            Agenda agenda = agendaOutputPort.findById(vote.getAgenda().getId());
            VotingSession votingSession = agenda.getVotingSession();

            //Verifica se a pauta e a sessão estão abertas
            if (agenda.getStatus().equals(AgendaStatus.OPEN)
                    && votingSession.getCloseDateTime().isAfter(TimeUtils.getDateTimeNow())
            ) {
                // Carrega todas as informações do associado
                Member member = memberOutputPort.findById(vote.getMember().getId());

                // Verifica se o associado já votou
                if (votingSession.getVotes().stream().anyMatch(v -> v.getMember().equals(member))) {
                    throw new MemberAlreadyVotedException("Associado já votou na pauta de ID: " + vote.getAgenda().getId());
                }

                // Voto realizado
                vote.setAgenda(agenda);
                vote.setMember(member);
                Vote savedVote = votingSessionOutputPort.saveVote(vote);

                // Atualiza a pauta e o associado ao voto
                savedVote.setAgenda(agenda);
                savedVote.setMember(member);

                // Adiciona o voto na sessão de votação
                votingSession.getVotes().add(savedVote);
                votingSessionOutputPort.save(votingSession);

                // Soma o voto na pauta
                agenda.addVote(savedVote);
                agendaOutputPort.save(agenda);

                // Retorna o voto realizado
                return savedVote;
            }
            throw new NotFoundVotingSessionException("Sessão de votação não encontrada para a pauta de ID: " + vote.getAgenda().getId());
        }
        throw new InvalidInformationVoteException("Informações inválidas para realizar o voto!");
    }
}