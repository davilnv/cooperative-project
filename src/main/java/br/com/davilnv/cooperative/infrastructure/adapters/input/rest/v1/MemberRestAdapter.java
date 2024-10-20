package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1;

import br.com.davilnv.cooperative.application.ports.input.CreateMemberUseCase;
import br.com.davilnv.cooperative.application.ports.input.GetMemberUseCase;
import br.com.davilnv.cooperative.domain.exception.MemberAlreadyExistsException;
import br.com.davilnv.cooperative.domain.exception.NotFoundMemberException;
import br.com.davilnv.cooperative.domain.model.Member;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto.MemberPostDto;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.mapper.MemberPostDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return new ResponseEntity<>(createMemberUseCase.createMember(member), HttpStatus.CREATED);
        } catch (MemberAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<?> getMember(@PathVariable UUID memberId) {
        try {
            Member member = getMemberUseCase.getMember(memberId);
            return ResponseEntity.ok(member);
        } catch (NotFoundMemberException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<?> getMemberByCpf(@PathVariable String cpf) {
        try {
            Member member = getMemberUseCase.fgetMemberByCpf(cpf);
            return ResponseEntity.ok(member);
        } catch (NotFoundMemberException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllMembers() {
        try {
            return ResponseEntity.ok(getMemberUseCase.getAllMembers());
        } catch (NotFoundMemberException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
