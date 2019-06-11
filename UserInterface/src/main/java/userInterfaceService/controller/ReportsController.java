package userInterfaceService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import userInterfaceService.connections.monitor.monitorConnection;
import userInterfaceService.domain.OrganizationDetails;
import userInterfaceService.domain.OrganizationUser;
import userInterfaceService.domain.Report;

@Controller
public class ReportsController {
	
	private monitorConnection monitorConnection;
	
	@Autowired
	public void setMonitorConnection(monitorConnection monitorConnection) {
		this.monitorConnection = monitorConnection;
	}
	
	
	
    @RequestMapping(value = "/reports", method = RequestMethod.POST)
    public ModelAndView getReports() {
    	Report[] reports = this.monitorConnection.getReportsFromMonitor();
    	ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("reports", reports);
        modelAndView.setViewName("dashboard");
    	
        System.err.println("\n\nReport: " + reports[0] + "\n\n");
        
    	return modelAndView;
    }

}
