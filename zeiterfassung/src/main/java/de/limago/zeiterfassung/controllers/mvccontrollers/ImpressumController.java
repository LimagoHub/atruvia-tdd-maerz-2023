package de.limago.zeiterfassung.controllers.mvccontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ImpressumController {

	@GetMapping("/public/impressum")
	public String impressum() {
		return "public/impressum";
	}

}
