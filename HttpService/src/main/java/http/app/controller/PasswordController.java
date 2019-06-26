package http.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import http.app.model.User;
import http.app.model.UserService;

@Controller
public class PasswordController {

	private UserService userService;

	@Autowired
	public PasswordController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(path="/forgotPassword", method=RequestMethod.GET)
	public String passwordReset() {
		return "passReset.html";
	}

	@RequestMapping(path="/changePassword", method=RequestMethod.POST)
	public String storeNewPassword(@ModelAttribute("user") User user,
			final HttpServletRequest request, Model model) {

		if(!userService.resetPassword(user)) {
			model.addAttribute("error", "User doesn't exist");
			return "passReset.html";
		}
		return "successChangePassword.html";
	}

}
