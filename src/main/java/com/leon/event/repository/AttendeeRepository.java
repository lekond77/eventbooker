package com.leon.event.repository;

import org.springframework.data.repository.CrudRepository;

import com.leon.event.model.Attendee;

public interface AttendeeRepository extends CrudRepository<Attendee, Long>{

	Attendee findByEmail(String email);

}
