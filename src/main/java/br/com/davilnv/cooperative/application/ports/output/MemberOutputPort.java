package br.com.davilnv.cooperative.application.ports.output;

import br.com.davilnv.cooperative.domain.exception.MemberAlreadyExistsException;
import br.com.davilnv.cooperative.domain.exception.NotFoundMemberException;
import br.com.davilnv.cooperative.domain.model.Member;

import java.util.List;
import java.util.UUID;

public interface MemberOutputPort {

    Member save(Member member) throws MemberAlreadyExistsException;

    Member findById(UUID memberId) throws NotFoundMemberException;

    Member findByCpf(String cpf) throws NotFoundMemberException;

    List<Member> findAll() throws NotFoundMemberException;

}
