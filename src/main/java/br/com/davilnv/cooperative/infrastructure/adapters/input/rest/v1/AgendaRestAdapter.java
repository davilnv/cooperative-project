package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1;

import br.com.davilnv.cooperative.application.ports.input.CreateAgendaUseCase;
import br.com.davilnv.cooperative.application.ports.input.GetAgendaUseCase;
import br.com.davilnv.cooperative.domain.exception.NotFoundAgendaException;
import br.com.davilnv.cooperative.domain.model.Agenda;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto.AgendaGetDto;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto.AgendaPostDto;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.mapper.AgendaGetDtoMapper;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.mapper.AgendaPostDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/agenda")
public class AgendaRestAdapter {

    private final CreateAgendaUseCase createAgendaUseCase;
    private final GetAgendaUseCase getAgendaUseCase;

    public AgendaRestAdapter(CreateAgendaUseCase createAgendaUseCase, GetAgendaUseCase getAgendaUseCase) {
        this.createAgendaUseCase = createAgendaUseCase;
        this.getAgendaUseCase = getAgendaUseCase;
    }

    @PostMapping
    public ResponseEntity<Agenda> createAgenda(@Valid @RequestBody AgendaPostDto agendaDto) {
        Agenda agenda = AgendaPostDtoMapper.toDomain(agendaDto);
        return new ResponseEntity<>(createAgendaUseCase.createAgenda(agenda), HttpStatus.CREATED);
    }

    @GetMapping("/{agendaId}")
    public ResponseEntity<?> getAgenda(@PathVariable UUID agendaId) {
        try {
            Agenda agenda = getAgendaUseCase.getAgenda(agendaId);
            return ResponseEntity.ok(AgendaGetDtoMapper.toDto(agenda));
        } catch (NotFoundAgendaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<AgendaGetDto>> getAllAgendas() {
        try {
            return ResponseEntity.ok(getAgendaUseCase.getAllAgendas()
                    .stream()
                    .map(AgendaGetDtoMapper::toDto)
                    .toList());
        } catch (NotFoundAgendaException e) {
            return new ResponseEntity<>(List.of(), HttpStatus.NOT_FOUND);
        }
    }

}
