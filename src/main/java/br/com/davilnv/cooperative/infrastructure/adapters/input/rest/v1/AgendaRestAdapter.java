package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1;

import br.com.davilnv.cooperative.domain.exception.NotFoundAgendaException;
import br.com.davilnv.cooperative.domain.model.Agenda;
import br.com.davilnv.cooperative.domain.service.AgendaService;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto.AgendaGetDto;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto.AgendaPostDto;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.mapper.AgendaGetDtoMapper;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.mapper.AgendaPostDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/agenda")
public class AgendaRestAdapter {

    private final AgendaService agendaService;

    public AgendaRestAdapter(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @PostMapping
    public ResponseEntity<Agenda> createAgenda(@RequestBody AgendaPostDto agendaDto) {
        Agenda agenda = AgendaPostDtoMapper.toDomain(agendaDto);
        return new ResponseEntity<>(agendaService.createAgenda(agenda), HttpStatus.CREATED);
    }

    @GetMapping("/{agendaId}")
    public ResponseEntity<?> getAgenda(@PathVariable UUID agendaId) throws NotFoundAgendaException {
        try {
            Agenda agenda = agendaService.getAgenda(agendaId);
            return ResponseEntity.ok(AgendaGetDtoMapper.toDto(agenda));
        } catch (NotFoundAgendaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<AgendaGetDto>> getAllAgendas() {
        return ResponseEntity.ok(agendaService.getAllAgendas()
                .stream()
                .map(AgendaGetDtoMapper::toDto)
                .toList());
    }

}
