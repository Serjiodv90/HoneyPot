package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.dal.UserDao;
import com.example.demo.model.User;
import com.example.demo.model.UserService;

@Controller
public class UserController {

	@Autowired
	UserDao dao;
	@Autowired
	UserService service;

	@RequestMapping(value="/")
	public String home() {
		return "Home.jsp";
	}

//	@RequestMapping("/addUser")
//	public String addUser(User user) {
//		dao.save(user);
//		return "Home.jsp";
//	}

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(User user) {
		return service.login(user);
	}
}
