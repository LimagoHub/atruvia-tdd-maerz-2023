package de.limago.zeiterfassung.controllers.restcontrollers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class InfoController {

	@Value("${InfoController.version}")
	private String version = "unknown";
	
	@Value("${spring.profiles.active}")
	private String profile = "unknown";
	
	@GetMapping("/activeProfile")
	public String getProfile() {
		return profile;
	}
	
	//TODO: Please Delet this comment
	@GetMapping("/version")
	public String getVersion() {
		return version;
	}
	
	
	
}
