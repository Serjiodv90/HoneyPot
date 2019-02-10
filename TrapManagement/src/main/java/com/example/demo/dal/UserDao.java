package com.example.demo.dal;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.User;

public interface UserDao extends CrudRepository<User, String>{
	
}
