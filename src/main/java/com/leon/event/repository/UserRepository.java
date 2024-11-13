package com.leon.event.repository;

import org.springframework.data.repository.CrudRepository;

import com.leon.event.model.User;

public interface UserRepository extends CrudRepository<User, Long>{

}
