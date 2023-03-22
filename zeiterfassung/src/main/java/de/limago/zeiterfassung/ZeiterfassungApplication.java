package de.limago.zeiterfassung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tngtech.jgiven.integration.spring.EnableJGiven;

@SpringBootApplication
@EnableJGiven
public class ZeiterfassungApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZeiterfassungApplication.class, args);
	
	}
	
	

} 
 