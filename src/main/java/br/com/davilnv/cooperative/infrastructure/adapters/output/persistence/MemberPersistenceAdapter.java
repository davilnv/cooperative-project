package br.com.davilnv.cooperative.infrastructure.adapters.output.persistence;

import br.com.davilnv.cooperative.application.ports.output.MemberOutputPort;
import br.com.davilnv.cooperative.domain.exception.MemberAlreadyExistsException;
import br.com.davilnv.cooperative.domain.exception.NotFoundMemberException;
import br.com.davilnv.cooperative.domain.model.Member;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.entity.MemberEntity;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.mapper.MemberMapper;
import br.com.davilnv.cooperative.infrastructure.adapters.output.persistence.repository.MemberRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class MemberPersistenceAdapter implements MemberOutputPort {

    private final MemberRepository memberRepository;

    public MemberPersistenceAdapter(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member save(Member member) throws MemberAlreadyExistsException {
        try {
            findByCpf(member.getCpf());
            throw new MemberAlreadyExistsException("Associado já cadastrado para o CPF: " + member.getCpf());
        } catch (NotFoundMemberException e) {
            MemberEntity memberEntity = MemberMapper.toEntity(member);
            return MemberMapper.toDomain(memberRepository.save(memberEntity));
        } catch (MemberAlreadyExistsException e) {
            throw e;
        }
    }

    @Override
    public Member findById(UUID memberId) throws NotFoundMemberException {
        Optional<MemberEntity> memberEntityOptional = memberRepository.findById(memberId);
        if (memberEntityOptional.isPresent()) {
            return MemberMapper.toDomain(memberEntityOptional.get());
        }
        throw new NotFoundMemberException("Associado não encontrado para o ID: " + memberId);
    }

    @Override
    public Member findByCpf(String cpf) throws NotFoundMemberException {
        Optional<MemberEntity> memberEntityOptional = memberRepository.findByCpf(cpf);
        if (memberEntityOptional.isPresent()) {
            return MemberMapper.toDomain(memberEntityOptional.get());
        }
        throw new NotFoundMemberException("Associado não encontrado para o CPF: " + cpf);
    }

    @Override
    public List<Member> findAll() throws NotFoundMemberException {
        List<Member> members = memberRepository.findAll()
                .stream()
                .map(MemberMapper::toDomain)
                .toList();

        if (members.isEmpty()) {
            throw new NotFoundMemberException("Nenhum associado encontrado");
        }
        return members;
    }
}
