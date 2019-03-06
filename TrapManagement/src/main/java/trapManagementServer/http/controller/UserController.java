package trapManagementServer.http.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import trapManagementServer.http.dal.UserDao;
import trapManagementServer.http.model.User;
import trapManagementServer.http.model.UserService;

@Controller
public class UserController {

	@Autowired
	UserDao dao;
	@Autowired
	UserService service;

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
		return "Home.jsp";
	}

	@RequestMapping(value="/Home")
	public String press() {
		System.out.println("in press!!!!!!!!!!!!!");
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
