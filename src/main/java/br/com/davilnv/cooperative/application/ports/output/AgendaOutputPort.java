package br.com.davilnv.cooperative.application.ports.output;

import br.com.davilnv.cooperative.domain.exception.NotFoundAgendaException;
import br.com.davilnv.cooperative.domain.model.Agenda;

import java.util.List;
import java.util.UUID;

public interface AgendaOutputPort {

    Agenda save(Agenda agenda);

    Agenda findById(UUID agendaId) throws NotFoundAgendaException;

    List<Agenda> findAll() throws NotFoundAgendaException;

}
