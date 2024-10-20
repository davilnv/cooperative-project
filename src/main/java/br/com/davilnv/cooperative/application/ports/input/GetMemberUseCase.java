package br.com.davilnv.cooperative.application.ports.input;

import br.com.davilnv.cooperative.domain.exception.NotFoundMemberException;
import br.com.davilnv.cooperative.domain.model.Member;

import java.util.List;
import java.util.UUID;

public interface GetMemberUseCase {

    Member getMember(UUID memberId) throws NotFoundMemberException;

    Member fgetMemberByCpf(String cpf) throws NotFoundMemberException;

    List<Member> getAllMembers() throws NotFoundMemberException;

}
