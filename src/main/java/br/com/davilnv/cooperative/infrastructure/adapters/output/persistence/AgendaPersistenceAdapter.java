package br.com.davilnv.cooperative.infrastructure.adapters.output.persistence;

import br.com.davilnv.cooperative.application.ports.output.AgendaOutputPort;
import br.com.davilnv.cooperative.domain.exception.NotFoundAgendaException;
import br.com.davilnv.cooperative.domain.model.Agenda;
import br.com.davilnv.cooperative.domain.model.Vote;
import br.com.davilnv.cooperative.domain.model.VotingSession;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.entity.AgendaEntity;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.entity.VotingSessionEntity;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.mapper.AgendaMapper;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.mapper.VotingSessionVoteEntityMapper;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.repository.AgendaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AgendaPersistenceAdapter implements AgendaOutputPort {

    private final AgendaRepository agendaRepository;

    public AgendaPersistenceAdapter(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    @Override
    public Agenda save(Agenda agenda) {
        AgendaEntity agendaEntity = AgendaMapper.toEntity(agenda);
        Agenda agendaCreated = AgendaMapper.toDomain(agendaRepository.save(agendaEntity));
        agendaCreated.setVotingSession(agenda.getVotingSession());
        return agendaCreated;
    }

    @Override
    public Agenda findById(UUID agendaId) throws NotFoundAgendaException {
        Optional<AgendaEntity> agendaEntityOptional = agendaRepository.findById(agendaId);
        if (agendaEntityOptional.isPresent()) {
            AgendaEntity agendaEntity = agendaEntityOptional.get();
            return getAgenda(agendaEntity);
        }
        throw new NotFoundAgendaException("Agenda não encontrada para o ID: " + agendaId);
    }

    @Override
    public List<Agenda> findAll() throws NotFoundAgendaException {
        List<Agenda> agendas = agendaRepository.findAll()
                .stream()
                .map(this::getAgenda).toList();

        if (agendas.isEmpty()) {
            throw new NotFoundAgendaException("Nenhuma agenda encontrada");
        }
        return agendas;
    }

    private Agenda getAgenda(AgendaEntity agendaEntity) {
        Agenda agenda = AgendaMapper.toDomain(agendaEntity);
        VotingSessionEntity votingSessionEntity = agendaEntity.getVotingSession();
        VotingSession votingSession = agenda.getVotingSession();

        if (votingSessionEntity != null
                && votingSessionEntity.getVotes() != null
                && !votingSessionEntity.getVotes().isEmpty()
        ) {
            List<Vote> votes = votingSessionEntity.getVotes()
                    .stream()
                    .map(vote -> VotingSessionVoteEntityMapper.toDomain(vote, agendaEntity))
                    .toList();
            votingSession.setVotes(votes);
        }

        return agenda;
    }
}
