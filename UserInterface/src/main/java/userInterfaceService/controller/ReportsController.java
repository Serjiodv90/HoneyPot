package userInterfaceService.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import reactor.netty.http.server.HttpServerResponse;
import userInterfaceService.connections.monitor.monitorConnection;
import userInterfaceService.domain.OrganizationDetails;
import userInterfaceService.domain.OrganizationUser;
import userInterfaceService.domain.Report;
import userInterfaceService.service.CustomUserDetailsService;

@Controller

public class ReportsController {
	
	private monitorConnection monitorConnection;
	private final String TRAPS_FILE_NAME = "templates.rar";
	
	
	
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
    
    
    @RequestMapping(value="/download", method=RequestMethod.GET)
    public void downloadTraps (HttpServletResponse response) throws IOException {
    	
    	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    	File trapsZipFile = new File(classLoader.getResource(this.TRAPS_FILE_NAME).getFile());
    	
    	if(!trapsZipFile.exists()) {
    		System.err.println("NO FILE BITCH!");
    		String errorMsg = "Sorry No File Exists!";
    		OutputStream outputStream = response.getOutputStream();
    		outputStream.write(errorMsg.getBytes(Charset.forName("UTF-8")));
    		outputStream.close();
    	}
    	
    	String mimeType = URLConnection.guessContentTypeFromName(trapsZipFile.getName());
    	if(mimeType == null) {
    		System.err.println("couldn't guess type mime");
    		mimeType = "application/octet-stream";
    	}
    	
    	System.err.println("Mime type: " + mimeType);
    	response.setContentType(mimeType);
    	
    	response.setHeader("Content-Disposition", String.format("inline; filename=\"" + trapsZipFile.getName() + "\""));
    	
    	response.setContentLength((int)trapsZipFile.length());
    	InputStream inputStream = new BufferedInputStream(new FileInputStream(trapsZipFile));
    	FileCopyUtils.copy(inputStream, response.getOutputStream());
    	
    	
    	 /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
        //response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
         
    }

}
