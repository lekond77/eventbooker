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
	public Event createEvent(Event event) {
		if (isValidEvent(event)) {
			return eventRepository.save(event);
		}
		return null;
	}

	// udapte Event
	public Event updateEvent(Event event) {

		Optional<Event> optionalUpdatedEvent = getEvent(event.getId());

		if (optionalUpdatedEvent.isPresent()) {

			Event updatedEvent = optionalUpdatedEvent.get();

			String title = event.getTitle();
			String description = event.getDescription();
			String imageUrl = event.getImageUrl();
			String eventDate = event.getDate();

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

			return eventRepository.save(updatedEvent);
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

	private boolean isValidEvent(Event event) {

		return (event.getTitle() != null && event.getDescription() != null && event.getImageUrl() != null);
	}

}