package com.leon.event.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.leon.event.model.Event;
import com.leon.event.service.EventService;
import com.leon.event.service.FileService;

@Controller
public class EventController {

	@Autowired
	private EventService eventService;
	
	@Autowired 
	private FileService fileService;
	

	@Autowired
	public EventController(FileService fileService) {
		this.fileService = fileService;
	}

	
	private void events(Model model) {
		Iterable<Event> events = eventService.getEvents();

		model.addAttribute("events", events);
	}
	
	@GetMapping("/")
	public String home(Model model) {
		events(model);
		return "home";
	}

	
	@GetMapping("/admin/events")
	public String getEvents(Model model) {

		events(model);
		return "event/events";
	}

	@GetMapping("/admin/events/new")
	public String createEvent(Model model) {

		model.addAttribute("event", new Event());
		return "event/add";
	}

	@GetMapping("/admin/events/edit/{id}")
	public String editEvent(@PathVariable final Long id, Model model) {

		Optional<Event> event = eventService.getEvent(id);

		if (event.isPresent()) {
			model.addAttribute("event", event.get());

			return "event/edit";
		}

		return "redirect:admin/event/events";
	}

	@PostMapping("/admin/events/new")
	public ModelAndView createEvent(@ModelAttribute Event event, RedirectAttributes redirectAttribute, Model model, @RequestParam MultipartFile imageUrl) {

		//System.out.println(imageUrl);
		
		fileService.storeFile(imageUrl);
		
		
		Event createdEvent = eventService.createEvent(event);
		
	
		if (createdEvent != null) {

			redirectAttribute.addFlashAttribute("success", "L'évènement ajouté avec succès");
			return new ModelAndView("redirect:new");
		} else {
			model.addAttribute("error", "Une erreur s'est produite lors de l'ajout");
		}

		return new ModelAndView("event/add");
	}

	@PostMapping("/admin/events/update")
	public String updateEvent(@ModelAttribute Event event, Model model) {

		eventService.updateEvent(event);
		
		return "redirect:/admin/events";
	}

	@GetMapping("/admin/events/delete/{id}")
	public String deleteEvent(@PathVariable final Long id) {

		if (id != null) {
			eventService.deleteEvent(id);
		}

		return "redirect:/admin/events";
	}

}
