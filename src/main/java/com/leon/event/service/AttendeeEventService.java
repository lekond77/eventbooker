package com.leon.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leon.event.model.AttendeeEvent;
import com.leon.event.repository.AttendeeEventRepository;

@Service
public class AttendeeEventService {

	@Autowired
	private AttendeeEventRepository  atEvRepo;
	
	
	public AttendeeEvent createAttendeeEvent(AttendeeEvent atEv) {
		return atEvRepo.save(atEv);
	}
}
