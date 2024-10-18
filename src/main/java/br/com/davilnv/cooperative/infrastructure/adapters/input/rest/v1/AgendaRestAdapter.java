package br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1;

import br.com.davilnv.cooperative.domain.model.Agenda;
import br.com.davilnv.cooperative.domain.service.AgendaService;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.dto.AgendaPostDto;
import br.com.davilnv.cooperative.infrastructure.adapters.input.rest.v1.mapper.AgendaPostDtoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
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
        return ResponseEntity.ok(agendaService.createAgenda(agenda));
    }

    @GetMapping("/{agendaId}")
    public ResponseEntity<Agenda> getAgenda(@PathVariable UUID agendaId) {
        Optional<Agenda> agenda = agendaService.getAgenda(agendaId);
        return agenda.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
