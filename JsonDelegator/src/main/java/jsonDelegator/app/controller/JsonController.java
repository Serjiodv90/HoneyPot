package jsonDelegator.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jsonDelegator.app.connections.MonitorConnection;
import jsonDelegator.app.model.RequestFormat;
import jsonDelegator.app.service.JsonService;

@RestController
public class JsonController {

	private JsonService jsonService;
	
	@Autowired
	public JsonController(JsonService jsonService) {
		this.jsonService = jsonService;
	}
	
	@RequestMapping(
			path="/delegateJson",
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE
			)
	public RequestFormat[] delegateJson(@RequestBody RequestFormat[] requestArr) {
		System.err.println("hahaha");
		return this.jsonService.sendJsonToMonitor(requestArr);
	}
	
	@RequestMapping(
			path="/lol",
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE
			)
	public ArrayList<RequestFormat> send(@RequestBody ArrayList<RequestFormat> arr){
		System.err.println("hahaha");
		return arr;
	}
	
	@RequestMapping(path="/kuku", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public String getKuku() {
		System.err.println("kakakakaka");
		return new String("kuku"); 
	}
	
	
	
}
