package br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.repository;

import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.entity.VotingSessionVoteEntity;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.entity.VotingSessionVoteid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotingSessionVoteRepository extends JpaRepository<VotingSessionVoteEntity, VotingSessionVoteid> {
}
