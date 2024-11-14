package com.leon.event.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leon.event.model.Event;
import com.leon.event.repository.EventRepository;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;

	// Get all Event
	public Iterable<Event> getEvents() {
		return eventRepository.findAll();
	}

	// Save Event and return it
	public Event saveEvent(Event event) {
		Event savedEvent;
		if(event.getId() == null) {
			savedEvent = eventRepository.save(event);
		}else {
			savedEvent = updateEvent(event);
		}
		return savedEvent;
	}

	
	// udapte Event
	public Event updateEvent(Event event) {

		Optional<Event> optionalUpdatedEvent = getEvent(event.getId());

		if (optionalUpdatedEvent.isPresent()) {
			
			Event updatedEvent = optionalUpdatedEvent.get();

			String title = updatedEvent.getTitle();
			String description = updatedEvent.getDescription();
			String imageUrl = updatedEvent.getImageUrl();
			String eventDate = updatedEvent.getDate();

			if (title != null) {
				updatedEvent.setTitle(title);
			}

			if (description != null) {
				updatedEvent.setDescription(description);
			}

			if (imageUrl != null) {
				updatedEvent.setImageUrl(imageUrl);
			}

			if (eventDate != null) {
				updatedEvent.setDate(eventDate);
			}
			
			return updatedEvent;
		}
		
		return null;
	}

	// get single event
	public Optional<Event> getEvent(final Long id) {
		return eventRepository.findById(id);
	}

	// Delete event by id
	public void deleteEvent(final Long id) {
		eventRepository.deleteById(id);
	}

}