package br.com.davilnv.cooperative.domain.service;

import br.com.davilnv.cooperative.application.ports.input.CreateMemberUseCase;
import br.com.davilnv.cooperative.application.ports.input.GetMemberUseCase;
import br.com.davilnv.cooperative.application.ports.output.MemberOutputPort;
import br.com.davilnv.cooperative.domain.exception.MemberAlreadyExistsException;
import br.com.davilnv.cooperative.domain.exception.NotFoundMemberException;
import br.com.davilnv.cooperative.domain.model.Member;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MemberService implements CreateMemberUseCase, GetMemberUseCase {

    private final MemberOutputPort memberOutputPort;

    public MemberService(MemberOutputPort memberOutputPort) {
        this.memberOutputPort = memberOutputPort;
    }

    @Override
    public Member createMember(Member member) throws MemberAlreadyExistsException {
        return memberOutputPort.save(member);
    }

    @Override
    public Member getMember(UUID memberId) throws NotFoundMemberException {
        return memberOutputPort.findById(memberId);
    }

    @Override
    public Member fgetMemberByCpf(String cpf) throws NotFoundMemberException {
        return memberOutputPort.findByCpf(cpf);
    }

    @Override
    public List<Member> getAllMembers() throws NotFoundMemberException {
        return memberOutputPort.findAll();
    }

}
