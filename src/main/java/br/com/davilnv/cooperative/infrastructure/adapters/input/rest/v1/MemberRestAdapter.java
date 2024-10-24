package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1;

import br.com.davilnv.cooperative.application.ports.input.CreateMemberUseCase;
import br.com.davilnv.cooperative.application.ports.input.GetMemberUseCase;
import br.com.davilnv.cooperative.domain.exception.MemberAlreadyExistsException;
import br.com.davilnv.cooperative.domain.exception.NotFoundMemberException;
import br.com.davilnv.cooperative.domain.model.Member;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto.MemberGetDto;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto.MemberPostDto;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.mapper.MemberGetDtoMapper;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.mapper.MemberPostDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/member")
public class MemberRestAdapter {

    private final CreateMemberUseCase createMemberUseCase;
    private final GetMemberUseCase getMemberUseCase;

    public MemberRestAdapter(CreateMemberUseCase createMemberUseCase, GetMemberUseCase getMemberUseCase) {
        this.createMemberUseCase = createMemberUseCase;
        this.getMemberUseCase = getMemberUseCase;
    }

    @PostMapping
    public ResponseEntity<?> createMember(@Valid @RequestBody MemberPostDto memberDto) {
        try {
            Member member = MemberPostDtoMapper.toDomain(memberDto);
            Member savedMember = createMemberUseCase.createMember(member);
            return new ResponseEntity<>(MemberGetDtoMapper.toDto(savedMember), HttpStatus.CREATED);
        } catch (MemberAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<?> getMember(@PathVariable UUID memberId) {
        try {
            Member member = getMemberUseCase.getMember(memberId);
            return ResponseEntity.ok(MemberGetDtoMapper.toDto(member));
        } catch (NotFoundMemberException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<?> getMemberByCpf(@PathVariable String cpf) {
        try {
            Member member = getMemberUseCase.getMemberByCpf(cpf);
            return ResponseEntity.ok(MemberGetDtoMapper.toDto(member));
        } catch (NotFoundMemberException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<MemberGetDto>> getAllMembers() {
        try {
            List<Member> members = getMemberUseCase.getAllMembers();
            return ResponseEntity.ok(members.stream()
                    .map(MemberGetDtoMapper::toDto)
                    .toList());
        } catch (NotFoundMemberException e) {
            return new ResponseEntity<>(List.of(), HttpStatus.NOT_FOUND);
        }
    }
}
