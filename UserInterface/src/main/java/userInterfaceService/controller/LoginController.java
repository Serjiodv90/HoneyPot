
package userInterfaceService.controller;

import userInterfaceService.connections.trapManagement.TrapManagementConnection;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("organizationUser")
public class LoginController {

    
    private CustomUserDetailsService userService;
    private TrapManagementConnection trapManagementConnection;
    
    @Autowired
    public LoginController(CustomUserDetailsService userService, TrapManagementConnection trapManagementConnection) {
		this.userService = userService;
		this.trapManagementConnection = trapManagementConnection;
	}

    @RequestMapping(value = {"/","/home", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(name="error", required=false) String errorStr) {
        ModelAndView modelAndView = new ModelAndView();
                
        if(errorStr != null && !errorStr.isEmpty() && errorStr.equalsIgnoreCase("true"))
        	modelAndView.addObject("badLogin", "Incorrect email or password");
        
        modelAndView.setViewName("login");
        return modelAndView;
    }
    

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signup() {
    	
        ModelAndView modelAndView = new ModelAndView();
        OrganizationUser user = new OrganizationUser();
        modelAndView.addObject("organizationUser", user);	// can be used in the HTML as th:object
        modelAndView.addObject("organizationDetails", new OrganizationDetails());

        modelAndView.setViewName("signup");
        return modelAndView;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid OrganizationUser user, BindingResult bindingResult) {    	
        ModelAndView modelAndView = new ModelAndView();
        OrganizationUser organizationExists = userService.findUserByOrganization(user.getOrganization());
        
        if (organizationExists != null) {
            bindingResult
                    .rejectValue("organization", "error.user",
                            "There is already a user registered with the organization name provided");	//by the name of the field, access to errors 
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("signup");
        } else {
        	
            userService.saveUser(user);
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

    	status.setComplete();
       	this.trapManagementConnection.sendOrganizationDetailsToTrapManagement(details);
    	
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("redirect:/login");	//set the url - dashboard
    	return modelAndView;
    }
    

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboard(ModelAndView modelAndView) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        OrganizationUser user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", user);
        modelAndView.setViewName("dashboard");
        return modelAndView;
    }
}
