package com.eventosapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.eventosapp.model.Convidado;
import com.eventosapp.model.Evento;

public interface ConvidadoRepository extends CrudRepository<Convidado, String>{

	Iterable<Convidado>findByEvento(Evento evento);
	Convidado findByRg(String rg);
}
