package userInterfaceService.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import userInterfaceService.connections.monitor.monitorConnection;
import userInterfaceService.domain.OrganizationDetails;
import userInterfaceService.domain.OrganizationUser;
import userInterfaceService.domain.Report;
import userInterfaceService.service.CustomUserDetailsService;

@Controller

public class ReportsController {
	
	private monitorConnection monitorConnection;
	
	 @Autowired
	private CustomUserDetailsService userService;
	
	@Autowired
	public void setMonitorConnection(monitorConnection monitorConnection) {
		this.monitorConnection = monitorConnection;
	}
	
	
	
    @RequestMapping(value = "/reports", method = RequestMethod.POST)
    public ModelAndView getReports( String type, String fromDate) {
    	
        System.err.println("n\nIn Reports controller\nType: " + type + "From date: " + fromDate + "\n\n");

    	Report[] reports = this.monitorConnection.getReportsFromMonitor();
    	ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("reports", reports);
        modelAndView.setViewName("dashboard");
        
        
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        OrganizationUser user = userService.findUserByEmail(auth.getName());
        System.err.println("\n\ncurrent user: " + user);
        
        modelAndView.addObject("currentUser", user);
        
    	
        System.err.println("\n\nReport: " + reports[0] + "\n\n");
        
    	return modelAndView;
    }

}
