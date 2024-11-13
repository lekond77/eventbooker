package com.leon.event.repository;

import org.springframework.data.repository.CrudRepository;

import com.leon.event.model.Event;

public interface EventRepository extends CrudRepository<Event, Long>{

}
