/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterfaceService.controller;

import userInterfaceService.connectionToTrapManagement.TrapManagementConnection;
import userInterfaceService.domain.FakeUser;
import userInterfaceService.domain.OrganizationDetails;
import userInterfaceService.domain.OrganizationUser;
import userInterfaceService.service.CustomUserDetailsService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("organizationUser")
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
        modelAndView.addObject("organizationDetails", new OrganizationDetails());

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
        	System.out.println("user doesn't exist: \n" + user);
        	
       //     userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("organizationUser", user);
            OrganizationDetails details = new OrganizationDetails();
           
            details.addFakeUser(new FakeUser());
            modelAndView.addObject("organizationDetails", details);
        

            FlashMap map = new FlashMap();
            map.put("organizationUser", user);
            modelAndView.setViewName("signup");

        }
        return modelAndView;
    }
    
    
    @RequestMapping(value="/storeDetails", method=RequestMethod.POST)
    public ModelAndView setOrganizationDetails(OrganizationDetails details, BindingResult bindingResult,
    		@ModelAttribute("organizationUser") OrganizationUser user, SessionStatus status) {
    	
    	System.out.println("LoginController.setOrganizationDetails()");
    	System.err.println("Print the fucking user: " + user);
    	System.out.println("\nDetails: \n" + details);
    	
    	status.setComplete();
    	
    	System.err.println("\n\nSending to trap management...");
    	new TrapManagementConnection().sendOrganizationDetails(details);
    	
    	
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("redirect:/dashboard");	//set the url - dashboard
    	return modelAndView;
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        OrganizationUser user = userService.findUserByEmail(auth.getName());
        
        System.out.println("LoginController.dashboard()");
        System.err.println("Auth user: " + user);
        
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
