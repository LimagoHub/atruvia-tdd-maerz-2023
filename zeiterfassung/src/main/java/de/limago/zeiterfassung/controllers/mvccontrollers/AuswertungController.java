package de.limago.zeiterfassung.controllers.mvccontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuswertungController {
	@GetMapping("/auswertung")
	public String auswertung() {
		return "/auswertung/index";
	}

}
