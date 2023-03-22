package de.limago.zeiterfassung.controllers.mvccontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ZeiterfassungController {
	@GetMapping("/zeiterfassung")
	public String zeiterfassung() {
		return "/zeiterfassung/index";
	}

}
