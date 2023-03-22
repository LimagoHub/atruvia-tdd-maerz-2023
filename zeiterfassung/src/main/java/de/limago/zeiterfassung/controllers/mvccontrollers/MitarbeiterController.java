package de.limago.zeiterfassung.controllers.mvccontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import de.limago.zeiterfassung.repositories.models.Mitarbeiter;
import de.limago.zeiterfassung.services.mitarbeiter.MitarbeiterServiceException;
import de.limago.zeiterfassung.services.mitarbeiter.MitarbeiterServiceImpl;

@Controller
public class MitarbeiterController {

	private final MitarbeiterServiceImpl mitarbeiterService;

	public MitarbeiterController(MitarbeiterServiceImpl mitarbeiterService) {
		this.mitarbeiterService = mitarbeiterService;
	}

	@GetMapping("/mitarbeiter")
	public String mitarbeiter(Model model) {
		try {
			model.addAttribute("mitarbeiter", mitarbeiterService.erzeugeMitarbeiterliste());
			return "/mitarbeiter/index";
		} catch (MitarbeiterServiceException e) {
			return "mitarbeiter/error";
		}
	}

	@GetMapping("/mitarbeiter/bearbeiten/{id}")
	public String mitarbeiterBearbeiten(@PathVariable final String id, Model model) {
		try {
			Mitarbeiter m = mitarbeiterService.erzeugeMitarbeiterliste().stream().filter(u -> u.getUsername().equals(id)).findFirst().orElseThrow(()->new MitarbeiterServiceException("Kein Mitarbeiter mit dieser Id gefunden."));
			model.addAttribute("mitarbeiter", m);
			return "/mitarbeiter/bearbeiten";
		} catch (MitarbeiterServiceException e) {
			return "mitarbeiter/error";
		}
	}

}
