package trapManagement.app.trapsGenerator;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import trapManagement.app.model.fakeTrapUsers.FakeUser;

@Component
public class TrapsManager extends HandlerInterceptorAdapter {
	
	
	private TrapsGenerator trapGenerator;
	
	@Autowired
	public void setTrapGenerator(TrapsGenerator trapGenerator) {
		this.trapGenerator = trapGenerator;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex) throws Exception {

		
		System.err.println("TrapsManager.afterConcurrentHandlingStarted()");
		trapGenerator.createTraps();
	}

}
