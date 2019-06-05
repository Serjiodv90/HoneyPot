package http.app.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import http.app.dal.UserDao;
import http.app.logging.HttpRequestsInterceptor;
import http.app.model.User;
import http.app.model.UserService;

@Controller
public class UserController {

//	@Autowired
//	UserDao dao;
	@Autowired
	UserService service;
	@Autowired
	HttpRequestsInterceptor httpRequestInterceptor;

	@GetMapping(value="/")
	public String home(HttpServletRequest request) {
		Integer integer = (Integer) request.getSession()
				.getAttribute("sessionNumber");        // create session if not exists and get attribute
		if (integer == null) {
			integer = new Integer(0);
			integer++;
			request.getSession().setAttribute("sessionNumber", integer);           // replace session attribute			
		} else {
			integer++;
			request.getSession().setAttribute("sessionNumber", integer);            // replace session attribute
		}
//		java.util.Map<String, Integer> hitCounter = new HashMap<>();
//		hitCounter.put("Hit Counter", integer);
		return "afekaLogin.jsp";
	}

	@RequestMapping(value="/Home")
	public String press(HttpServletRequest request) {
		return "afekaLogin.jsp";
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
