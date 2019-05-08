/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterfaceService.controller;

import userInterfaceService.domain.OrganizationUser;
import userInterfaceService.service.CustomUserDetailsService;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    private CustomUserDetailsService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        System.out.println("LoginController.login()\n" + modelAndView.toString());
        return modelAndView;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signup() {
    	System.out.println("LoginController.signup()");
    	
        ModelAndView modelAndView = new ModelAndView();
        OrganizationUser user = new OrganizationUser();
        modelAndView.addObject("organizationUser", user);	// can be used in the HTML as th:object
        modelAndView.setViewName("signup");
        return modelAndView;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid OrganizationUser user, BindingResult bindingResult) {
    	System.out.println("LoginController.createNewUser()");
    	
        ModelAndView modelAndView = new ModelAndView();
        
        OrganizationUser organizationExists = userService.findUserByOrganization(user.getOrganization());
        
        
        if (organizationExists != null) {
        	
        	System.out.println("Organization already exists!");
        	
            bindingResult
                    .rejectValue("organization", "error.user",
                            "There is already a user registered with the username provided");	//by the name of the field, access to errors 
            
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("signup");
        } else {
        	System.out.println("user doesn't exist");
        	
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new OrganizationUser());
            modelAndView.setViewName("signup");

        }
        return modelAndView;
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        OrganizationUser user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", user);
//        modelAndView.addObject("fullName", "Welcome " + user.getFullname());
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.setViewName("dashboard");
        return modelAndView;
    }
    
    @RequestMapping(value = {"/","/home"}, method = RequestMethod.GET)
    public ModelAndView home() {
    	System.out.println("LoginController.home()");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("stam");	// the name of the html FILE!!!!!
        return modelAndView;
    }

}
