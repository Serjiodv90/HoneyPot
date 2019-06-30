package jsonDelegator.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
		return this.jsonService.sendJsonToMonitor(requestArr);
	}


	
	
}
