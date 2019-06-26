package http.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String home(@RequestParam(name="error", required=false) String errorStr, HttpServletRequest request,  Model model) {
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
		
		System.out.println(errorStr);

		if(errorStr != null && !errorStr.isEmpty() && errorStr.equalsIgnoreCase("true")) {
			model.addAttribute("error", "Incorrect email or password");
			
		}
		
		return "afekaLogin.html";
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
		
		return "AfterLogin.html";
	}
	

//	@RequestMapping(value="/login", method=RequestMethod.POST)
//	public String login(User user) {
//		return service.login(user);
//	}
	
	@RequestMapping(value="/logout-success")
	public String logoutPage() {
		return "afekaLogin.html";
	}

}
