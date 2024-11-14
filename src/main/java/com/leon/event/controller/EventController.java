package com.leon.event.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.leon.event.model.Event;
import com.leon.event.service.EventService;

@Controller
public class EventController {

	@Autowired
	private EventService eventService;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/admin/events/new")
	public String createEvent(Model model) {
		
		model.addAttribute("event", new Event());
		return "event/add";
	}
	
	@GetMapping("/admin/events")
	public String events(Model model) {
		
		Iterable<Event> events = eventService.getEvents();
		
		model.addAttribute("events", events);
		
		return "event/events";
	}
	
	@GetMapping("/admin/events/edit/{id}")
	public String editEvent(@PathVariable final Long id,  Model model) {
		
		Optional<Event> event = eventService.getEvent(id);
		
		if(event.isPresent()) {
			model.addAttribute("employee", event);
			
			return "event/edit";
		}

		return "redirect:/event/events";
	}
	
	@PostMapping("/admin/events/save")
	public String saveEvent(@ModelAttribute Event event, Model model) {
		
		if(event != null) {
			eventService.saveEvent(event);
			model.addAttribute("success", "L'évènement ajouter avec succès");
		}else {
			model.addAttribute("error", "Une erreur s'est produite lors de l'ajout");
		}
		
		return "redirect:/admin/events/new";
	}
}
