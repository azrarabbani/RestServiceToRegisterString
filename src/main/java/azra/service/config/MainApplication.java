package azra.service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import azra.service.AzraServiceController;;

/**
 * Created by arabbani on 10/26/16.
 * sazrai@hotmail.com
 */

@SpringBootApplication
public class MainApplication extends SpringBootServletInitializer{
	
	
	private static final Logger logger = LoggerFactory.getLogger(AzraServiceController.class);
	
	/**
	 * Main method to start app
	 * @param args
	 */
	public static void main(String[] args){
		logger.info("Application Starting at port 8080");
		SpringApplication.run(MainApplication.class, args);
	}
	
}
