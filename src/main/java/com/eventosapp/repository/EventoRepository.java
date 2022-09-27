package com.eventosapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.eventosapp.model.Evento;


public interface EventoRepository extends CrudRepository<Evento, String>{

Evento findById(Long id);
}
