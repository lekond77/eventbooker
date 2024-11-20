package com.leon.event.controller;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.leon.event.model.Attendee;
import com.leon.event.model.AttendeeEvent;
import com.leon.event.model.Event;
import com.leon.event.request.AttendeeRequest;
import com.leon.event.service.AttendeeEventService;
import com.leon.event.service.AttendeeService;
import com.leon.event.service.EventService;
import com.leon.event.service.QrCodeService;

@RestController
public class AttendeeController {

	@Autowired
	private EventService eventService;

	@Autowired
	private AttendeeService attendeeService;

	@Autowired
	private QrCodeService qrCodeService;

	@Autowired
	private AttendeeEventService attendeeEventService;

	@PostMapping("/register")
	public ResponseEntity<Map<String, Object>> register(@RequestBody AttendeeRequest attendeeRequest) {

		if (!attendeeService.isValidAttendeeRequest(attendeeRequest)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(Map.of("message", "Les données ne sont pas correctes !"));
		}

		Optional<Event> OptionalEvent = eventService.getEvent(attendeeRequest.getEventId());

		if (!OptionalEvent.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "L'évènement non trouvé "));
		}

		Attendee attendee = attendeeService.getAttendeeByEmail(attendeeRequest.getEmail());

		if (attendee == null) {
			attendee = new Attendee(attendeeRequest.getEmail(), attendeeRequest.getName());
			attendee = attendeeService.createAttendee(attendee);
		}

		Event event = OptionalEvent.get();
		Long eventId = event.getId(), attendeeId = attendee.getId();

		if (attendee.getEvents() != null) {
			boolean alreadyRegistered = attendee.getEvents().stream().anyMatch(e -> e.getEvent().equals(event));
			if (alreadyRegistered) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(Map.of("message", "Vous êtes déjà inscrit à cet évènement"));
			}
		}

		try {

			String qrCodeData = "http://localhost:8087/verify/" + attendeeId + "/" + eventId;

			String subPath = "attendee_" + String.valueOf(attendeeId);

			String qrCodePath = subPath + "/" + qrCodeService.generateQRCode(qrCodeData, subPath);

			AttendeeEvent attendeeEvent = new AttendeeEvent();
			attendeeEvent.setAttendee(attendee);
			attendeeEvent.setEvent(event);
			attendeeEvent.setQrCodePath( qrCodePath);
			
			attendeeEventService.createAttendeeEvent(attendeeEvent);

			Map<String, Object> response = new HashMap<>();
			response.put("message", "Votre inscription pour cet évènement est bien enregistrée et votre badge généré.");
			response.put("qrCode", qrCodePath);

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Erreur lors de la génération du QR code"));
		}
	}

}
