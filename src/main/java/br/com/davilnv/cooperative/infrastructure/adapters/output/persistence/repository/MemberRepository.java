package br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.repository;

import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, UUID> {

    Optional<MemberEntity> findByCpf(String cpf);

}
