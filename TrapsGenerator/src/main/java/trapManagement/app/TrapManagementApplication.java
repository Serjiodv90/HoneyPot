package trapManagement.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
//@EnableAsync
public class TrapManagementApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(TrapManagementApplication.class, args);
	}

}
