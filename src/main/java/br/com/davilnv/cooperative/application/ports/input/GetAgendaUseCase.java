package br.com.davilnv.cooperative.application.ports.input;

import br.com.davilnv.cooperative.domain.exception.NotFoundAgendaException;
import br.com.davilnv.cooperative.domain.model.Agenda;

import java.util.List;
import java.util.UUID;

public interface GetAgendaUseCase {

    Agenda getAgenda(UUID agendaId) throws NotFoundAgendaException;

    List<Agenda> getAllAgendas() throws NotFoundAgendaException;

}
