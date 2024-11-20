package com.leon.event.repository;

import org.springframework.data.repository.CrudRepository;

import com.leon.event.model.AttendeeEvent;

public interface AttendeeEventRepository extends CrudRepository<AttendeeEvent, Long>{

}
