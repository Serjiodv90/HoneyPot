package userInterfaceService.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import userInterfaceService.connections.monitor.MonitorConnection;
import userInterfaceService.connections.trapManagement.TrapManagementConnection;
import userInterfaceService.domain.OrganizationUser;
import userInterfaceService.domain.Report;
import userInterfaceService.service.CustomUserDetailsService;

@Controller

public class DashboardController {
	
	private MonitorConnection monitorConnection;
	private TrapManagementConnection trapManagementConnection;
	private Environment env;
	private CustomUserDetailsService userService;
	
	@Autowired
	public DashboardController(MonitorConnection monitorConnection, 
								TrapManagementConnection trapManagementConnection, 
								CustomUserDetailsService userService,
								Environment env) {
		
		this.monitorConnection = monitorConnection;
		this.trapManagementConnection = trapManagementConnection;
		this.userService = userService;
		this.env = env;
	}
	
	
	
    @RequestMapping(
    		value = "/reports", 
    		method = RequestMethod.POST)
    public ModelAndView getReports(String type, String fromDate) {
   
        Report[] reports;
        
        if(type != null && !type.isEmpty() && !type.equalsIgnoreCase("all")) {
        		if(fromDate != null && !fromDate.isEmpty())
        			reports = this.monitorConnection.getReportsByDateAndType(fromDate, type);
        		else
                	reports = this.monitorConnection.getReportsByType(type);
        }
        else if(fromDate != null && !fromDate.isEmpty())
        	reports = this.monitorConnection.getReportsByDate(fromDate);
        else       
        	reports = this.monitorConnection.getAllReports();
        
    	ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("reports", reports);
        modelAndView.setViewName("dashboard");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        OrganizationUser user = userService.findUserByEmail(auth.getName());
        
        modelAndView.addObject("currentUser", user);
        
    	return modelAndView;
    }
    
    
    @RequestMapping(value="/download", method=RequestMethod.GET)
    public void downloadTraps (HttpServletResponse response) throws IOException {
    	
    	String trapsZipFilePath = this.trapManagementConnection.getTrapsDownloadPathFromTrapManagement();
    	if(!this.env.getProperty("service.machine").equalsIgnoreCase("localhost"))
    		trapsZipFilePath = System.getProperty("user.dir") + trapsZipFilePath;
    	File trapsZipFile = new File(trapsZipFilePath);
    	
    	if(!trapsZipFile.exists()) {
    		String errorMsg = "Sorry No File Exists!";
    		OutputStream outputStream = response.getOutputStream();
    		outputStream.write(errorMsg.getBytes(Charset.forName("UTF-8")));
    		outputStream.close();
    	}
    	
    	String mimeType = URLConnection.guessContentTypeFromName(trapsZipFile.getName());
    	if(mimeType == null) {
    		mimeType = "application/octet-stream";
    	}
    	
    	System.err.println("Mime type: " + mimeType);
    	response.setContentType(mimeType);
    	
    	 /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
    	response.setHeader("Content-Disposition", String.format("inline; filename=\"" + trapsZipFile.getName() + "\""));
    	
    	response.setContentLength((int)trapsZipFile.length());
    	InputStream inputStream = new BufferedInputStream(new FileInputStream(trapsZipFile));
    	FileCopyUtils.copy(inputStream, response.getOutputStream());
         
    }

}
