package com.eventosapp.controller;






import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eventosapp.model.Convidado;
import com.eventosapp.model.Evento;
import com.eventosapp.repository.ConvidadoRepository;
import com.eventosapp.repository.EventoRepository;

@Controller
public class EventoController {
	
	@Autowired
	private EventoRepository eventoRepository;
	
	@Autowired
	private ConvidadoRepository convidadoRepository;
	
	
	@RequestMapping(value ="/cadastrarEvento", method=RequestMethod.GET)
	public String form() {
		return "evento/formEvento";
	}
	
	
	@RequestMapping(value = "/cadastrarEvento", method=RequestMethod.POST)
	public String form(@Valid Evento evento, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			
			attributes.addFlashAttribute("mensagem", "CAMPO NÃO PODE ESTAR VAZIO!");
			return "redirect:/cadastrarEvento";
	}
		
			eventoRepository.save(evento);
		attributes.addFlashAttribute("mensagem", "EVENTO CADASTRADO COM SUCESSO");
		return "redirect:/cadastrarEvento";
	}
	
	@RequestMapping("/eventos")
	public ModelAndView listaEventos() {
		ModelAndView mv = new ModelAndView("indice");
		Iterable<Evento>eventos = eventoRepository.findAll();
		mv.addObject("eventos", eventos);
		
		return mv;
		
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ModelAndView detalhesEventos(@PathVariable("id")Long id) {
		Evento evento = eventoRepository.findById(id);
		ModelAndView mv = new ModelAndView("evento/detalhesEventos");
		mv.addObject("evento",evento);
		
		Iterable<Convidado> convidados = convidadoRepository.findByEvento(evento);
		mv.addObject("convidados", convidados);
		return mv;
	}
	
	@RequestMapping("/deletarEvento")
	public String deletarEvento(Long id) {

		Evento evento = eventoRepository.findById(id);
		eventoRepository.delete(evento);
		
		return "redirect:/eventos";
	}
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.POST)
	public String detalhesEventosPost(@PathVariable("id")Long id,@Valid Convidado convidado, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()){
			attributes.addFlashAttribute("mensagem", "Campo não pode estar vazio");
			return "redirect:/{id}";
			
		}
		
		Evento evento = eventoRepository.findById(id);
		convidado.setEvento(evento);
		convidadoRepository.save(convidado);
		attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso");

		
		return "redirect:/{id}";
	}
	
	@RequestMapping("/deletarConvidado")
	public String deletarConvidado(String rg) {

		Convidado convidado = convidadoRepository.findByRg(rg);
		convidadoRepository.delete(convidado);
		
		
		Evento evento = convidado.getEvento();	
		Long idLong = evento.getId();
		String id = "" + idLong;
		
		return "redirect:/" + id;
		
	
	}

}
