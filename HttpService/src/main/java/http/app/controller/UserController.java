package http.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import http.app.MyUserPrincipal;
import http.app.logging.HttpRequestsInterceptor;
import http.app.model.UserService;

@Controller
public class UserController {

//	@Autowired
//	UserDao dao;
	@Autowired
	UserService service;
	@Autowired
	HttpRequestsInterceptor httpRequestInterceptor;

	@RequestMapping(value="/login")
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

	@RequestMapping(value= {"/", "/home"})
	public String press(HttpServletRequest request) {
		System.err.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(user instanceof MyUserPrincipal) {
			System.err.println(((MyUserPrincipal)user).getUsername());
			String uName = (String) request.getSession()
					.getAttribute("userName");
			if(uName == null) {
				uName = new String(((MyUserPrincipal)user).getUsername());
				request.getSession().setAttribute("userName", uName);
			}
			
		}
		return "AfterLogin.jsp";
	}
	

//	@RequestMapping(value="/login", method=RequestMethod.POST)
//	public String login(User user) {
//		return service.login(user);
//	}
	
	@RequestMapping(value="/logout-success")
	public String logoutPage() {
		return "afekaLogin.jsp";
	}

}
