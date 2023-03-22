package de.limago.zeiterfassung.controllers.mvccontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WerkstattController {
	@GetMapping("/werkstatt")
	public String werkstatt() {
		return "/werkstatt/index";
	}

}
