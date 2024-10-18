package br.com.davilnv.cooperative.domain.service;

import br.com.davilnv.cooperative.application.ports.input.CreateAgendaUseCase;
import br.com.davilnv.cooperative.application.ports.input.GetAgendaUseCase;
import br.com.davilnv.cooperative.application.ports.output.AgendaOutputPort;
import br.com.davilnv.cooperative.domain.model.Agenda;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AgendaService implements CreateAgendaUseCase, GetAgendaUseCase {

    private final AgendaOutputPort agendaOutputPort;

    public AgendaService(AgendaOutputPort agendaOutputPort) {
        this.agendaOutputPort = agendaOutputPort;
    }

    @Override
    public Agenda createAgenda(Agenda agenda) {
        // Lógica para criar uma nova agenda
        return agendaOutputPort.save(agenda);
    }

    @Override
    public Optional<Agenda> getAgenda(UUID agendaId) {
        return agendaOutputPort.findById(agendaId);
    }

    @Override
    public List<Agenda> getAllAgendas() {
        return agendaOutputPort.findAll();
    }

}
