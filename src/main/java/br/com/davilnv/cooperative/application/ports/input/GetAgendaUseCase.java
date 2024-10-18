package br.com.davilnv.cooperative.application.ports.input;

import br.com.davilnv.cooperative.domain.model.Agenda;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GetAgendaUseCase {

    Optional<Agenda> getAgenda(UUID agendaId);

    List<Agenda> getAllAgendas();

}
