package jsonDelegator.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jsonDelegator.app.connections.MonitorConnection;
import jsonDelegator.app.model.RequestFormat;

@Service
public class JsonService {
	
	private MonitorConnection monitorConnection;
	
	@Autowired
	public JsonService(MonitorConnection monitorConnection) {
		this.monitorConnection = monitorConnection;
	}
	
	
	public RequestFormat[] sendJsonToMonitor(RequestFormat[] requestArr) {
		return this.monitorConnection.sendJsonToMonitor(requestArr);
	}

}
