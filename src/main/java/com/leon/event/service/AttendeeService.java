package com.leon.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leon.event.model.Attendee;
import com.leon.event.repository.AttendeeRepository;
import com.leon.event.request.AttendeeRequest;

@Service
public class AttendeeService {

	@Autowired
	private AttendeeRepository attendeeRepositoty;
	
	public Attendee createAttendee(Attendee attendee) {
		
		return attendeeRepositoty.save(attendee);
	}
	
	
	public Attendee getAttendeeByEmail(String email) {
		return attendeeRepositoty.findByEmail(email);
	}
	public boolean isValidAttendeeRequest(AttendeeRequest request) {
		return request.getEventId() != null  && request.getEmail() != null && request.getName() != null;
	}
	
	
}
