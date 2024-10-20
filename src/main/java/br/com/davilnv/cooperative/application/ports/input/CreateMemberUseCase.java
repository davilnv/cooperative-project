package br.com.davilnv.cooperative.application.ports.input;

import br.com.davilnv.cooperative.domain.exception.MemberAlreadyExistsException;
import br.com.davilnv.cooperative.domain.model.Member;

public interface CreateMemberUseCase {

    Member createMember(Member member) throws MemberAlreadyExistsException;

}
