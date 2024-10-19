package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1;

import br.com.davilnv.cooperative.domain.exception.NotFoundAgendaException;
import br.com.davilnv.cooperative.domain.exception.RequiredAgendaException;
import br.com.davilnv.cooperative.domain.exception.TheresAlreadyOpenVotingSessionException;
import br.com.davilnv.cooperative.domain.model.VotingSession;
import br.com.davilnv.cooperative.domain.service.VotingSessionService;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto.VotingSessionPostDto;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.mapper.VotingSessionGetDtoMapper;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.mapper.VotingSessionPostDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/voting-session")
public class VotingSessionRestAdapter {

    private final VotingSessionService votingSessionService;

    public VotingSessionRestAdapter(VotingSessionService votingSessionService) {
        this.votingSessionService = votingSessionService;
    }

    @PostMapping("/open")
    public ResponseEntity<?> openVotingSession(@RequestBody VotingSessionPostDto votingSessionDto) {
        try {
            VotingSession votingSession = votingSessionService.createVotingSession(VotingSessionPostDtoMapper.toDomain(votingSessionDto));
            return new ResponseEntity<>(VotingSessionGetDtoMapper.toDto(votingSession), HttpStatus.CREATED);
        } catch (NotFoundAgendaException | RequiredAgendaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (TheresAlreadyOpenVotingSessionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
