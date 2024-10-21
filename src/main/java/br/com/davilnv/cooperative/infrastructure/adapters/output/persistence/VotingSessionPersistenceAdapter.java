package br.com.davilnv.cooperative.infrastructure.adapters.output.persistence;

import br.com.davilnv.cooperative.application.ports.output.VotingSessionOutputPort;
import br.com.davilnv.cooperative.domain.exception.NotFoundVotingSessionException;
import br.com.davilnv.cooperative.domain.model.Vote;
import br.com.davilnv.cooperative.domain.model.VotingSession;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.entity.VotingSessionEntity;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.entity.VotingSessionVoteEntity;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.mapper.VotingSessionMapper;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.mapper.VotingSessionVoteEntityMapper;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.repository.VotingSessionRepository;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.repository.VotingSessionVoteRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class VotingSessionPersistenceAdapter implements VotingSessionOutputPort {

    private final VotingSessionRepository votingSessionRepository;
    private final VotingSessionVoteRepository votingSessionVoteRepository;

    public VotingSessionPersistenceAdapter(VotingSessionRepository votingSessionRepository, VotingSessionVoteRepository votingSessionVoteRepository) {
        this.votingSessionRepository = votingSessionRepository;
        this.votingSessionVoteRepository = votingSessionVoteRepository;
    }

    @Override
    public VotingSession save(VotingSession votingSession) {
        VotingSessionEntity votingSessionEntity = VotingSessionMapper.toEntity(votingSession);

        // Mapeia os votos da sessão de votação
        List<VotingSessionVoteEntity> votingSessionVoteEntities = votingSession.getVotes()
                .stream()
                .map(VotingSessionVoteEntityMapper::toEntity)
                .toList();
        votingSessionEntity.setVotes(votingSessionVoteEntities);

        return VotingSessionMapper.toDomain(votingSessionRepository.save(votingSessionEntity));
    }

    @Override
    public Vote saveVote(Vote vote) {
        VotingSessionVoteEntity votingSessionVoteEntity = VotingSessionVoteEntityMapper.toEntity(vote);
        return VotingSessionVoteEntityMapper.toDomain(votingSessionVoteRepository.save(votingSessionVoteEntity));
    }

    @Override
    public VotingSession findById(UUID votingSessionId) throws NotFoundVotingSessionException {
        Optional<VotingSessionEntity> votingSessionEntityOptional = votingSessionRepository.findById(votingSessionId);
        if (votingSessionEntityOptional.isPresent()) {
            return VotingSessionMapper.toDomain(votingSessionEntityOptional.get());
        }
        throw new NotFoundVotingSessionException("Sessão de votação não encontrada para o ID: " + votingSessionId);
    }

    @Override
    public List<VotingSession> findAll() {
        return votingSessionRepository.findAll()
                .stream()
                .map(VotingSessionMapper::toDomain)
                .toList();
    }
}
