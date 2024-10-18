package br.com.davilnv.cooperative.application.ports.output;

import br.com.davilnv.cooperative.domain.model.Agenda;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AgendaOutputPort {

    Agenda save(Agenda agenda);

    Optional<Agenda> findById(UUID agendaId);

    List<Agenda> findAll();

}
