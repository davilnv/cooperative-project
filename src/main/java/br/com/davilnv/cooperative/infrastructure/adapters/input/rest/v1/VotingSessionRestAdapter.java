package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1;

import br.com.davilnv.cooperative.application.ports.input.CreateVotingSessionUseCase;
import br.com.davilnv.cooperative.application.ports.input.PerformVoteUseCase;
import br.com.davilnv.cooperative.domain.exception.*;
import br.com.davilnv.cooperative.domain.model.Vote;
import br.com.davilnv.cooperative.domain.model.VotingSession;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto.MemberVotePostDto;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto.VotingSessionPostDto;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.mapper.MemberVotePostDtoMapper;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.mapper.VoteGetDtoMapper;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.mapper.VotingSessionGetDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/voting-session")
public class VotingSessionRestAdapter {

    private final CreateVotingSessionUseCase createVotingSessionUseCase;
    private final PerformVoteUseCase performVoteUseCase;

    public VotingSessionRestAdapter(CreateVotingSessionUseCase createVotingSessionUseCase, PerformVoteUseCase performVoteUseCase) {
        this.createVotingSessionUseCase = createVotingSessionUseCase;
        this.performVoteUseCase = performVoteUseCase;
    }

    @PostMapping("/open")
    public ResponseEntity<?> openVotingSession(@Valid @RequestBody VotingSessionPostDto votingSessionDto) {
        try {
            VotingSession votingSession = createVotingSessionUseCase.createVotingSession(votingSessionDto.agendaId(), votingSessionDto.closeDateTime());
            return new ResponseEntity<>(VotingSessionGetDtoMapper.toDto(votingSession), HttpStatus.CREATED);
        } catch (NotFoundAgendaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (TheresAlreadyOpenVotingSessionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (RequiredAgendaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/vote")
    public ResponseEntity<?> vote(@Valid @RequestBody MemberVotePostDto memberVotePostDto) {
        try {
            Vote vote = MemberVotePostDtoMapper.toDomain(memberVotePostDto);
            Vote savedVote = performVoteUseCase.performVote(vote);
            return new ResponseEntity<>(VoteGetDtoMapper.toDto(savedVote), HttpStatus.CREATED);
        } catch (NotFoundAgendaException | NotFoundVotingSessionException | NotFoundMemberException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (MemberAlreadyVotedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (InvalidInformationVoteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
