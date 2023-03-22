package de.limago.zeiterfassung.controllers.mvccontrollers;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.RequestScope;

import de.limago.zeiterfassung.repositories.models.Zeitmodell;
import de.limago.zeiterfassung.services.zeitmodelle.ZeitmodellService;
import de.limago.zeiterfassung.services.zeitmodelle.ZeitmodellServiceException;

@Controller
@RequestScope
public class ZeitmodellController {

	private final ZeitmodellService zeitmodellService;
	private final LocalMessages messages;

	public ZeitmodellController(ZeitmodellService zeitmodellService) {
		this.zeitmodellService = zeitmodellService;
		this.messages = new LocalMessages();
	}

	@GetMapping("/zeitmodell")
	public String zeitmodell(
			@RequestParam(value = "showMessage", required = false, defaultValue = "false") boolean showMessage,
			Model model) {
		try {
			if (!showMessage)
				messages.clear();

			model.addAttribute("zeitmodelle", zeitmodellService.findeAlleZeitmodelle());
			model.addAttribute("messages", messages);

		} catch (ZeitmodellServiceException e) {
			messages.setError(e.getMessage());
		}

		return "zeitmodell/index";
	}

	@GetMapping("/zeitmodell/loeschen/{id}")
	public String zeitmodell(@PathVariable String id) {
		try {
			messages.clear();
			zeitmodellService.loescheZeitmodell(id);
			messages.setSuccess("Zeitmodell erfolgreich gelöscht!");

		} catch (ZeitmodellServiceException e) {
			messages.setError(e.getMessage());
		}
		return "forward:/zeitmodell?showMessage=true";

	}

	@GetMapping("/zeitmodell/neu")
	public String neuesZeitmodell(Model model) {
		messages.clear();
		Zeitmodell neu = Zeitmodell.builder().id(UUID.randomUUID().toString()).build();
		model.addAttribute("zeitmodell", neu);
		model.addAttribute("messages", messages);
		return "zeitmodell/bearbeiten";
	}

	@GetMapping("/zeitmodelle/anzeigen/{id}")
	public String anzeigenZeitmodell(@PathVariable String id, Model model) {
		try {
			messages.clear();
			Optional<Zeitmodell> optionalModell = zeitmodellService.findeZeitModellNachPrimaerSchluessel(id);
			Zeitmodell zeitmodell = optionalModell
					.orElseThrow(() -> new ZeitmodellServiceException("Modell nicht gefunden!"));
			model.addAttribute("zeitmodell", zeitmodell);
			model.addAttribute("messages", messages);
		} catch (ZeitmodellServiceException e) {
			messages.setError(e.getMessage());
			return "redirect:/zeitmodell?showMessage=true";
		}
		return "zeitmodell/anzeigen";
	}

	@GetMapping("/zeitmodell/bearbeiten/{id}")
	public String bearbeiteZeitmodell(@PathVariable String id, Model model) {
		try {
			messages.clear();
			Optional<Zeitmodell> optionalModell = zeitmodellService.findeZeitModellNachPrimaerSchluessel(id);
			Zeitmodell zeitmodell = optionalModell
					.orElseThrow(() -> new ZeitmodellServiceException("Modell nicht gefunden!"));
			model.addAttribute("zeitmodell", zeitmodell);
			model.addAttribute("messages", messages);
			if (zeitmodellService.isZeitmodellActive(zeitmodell)) {
				messages.setWarning("Zeitmodell ist aktiv. Nur Anzeigen möglich");
				return "zeitmodell/anzeigen";
			} else {
				return "zeitmodell/bearbeiten";
			}

		} catch (ZeitmodellServiceException e) {
			messages.setError(e.getMessage());
			return "forward:/zeitmodell?showMessage=true";
		}

	}

	@PostMapping("/zeitmodell/save")
	public String saveZeitmodell(@Valid @ModelAttribute Zeitmodell zeitmodell, BindingResult bindingResult,
			Model model) {
		try {

			model.addAttribute("zeitmodell", zeitmodell);
			model.addAttribute("messages", messages);
			if (bindingResult.hasErrors()) {
				return "zeitmodell/bearbeiten";
			}
			zeitmodellService.speichereOderAendereZeitmodell(zeitmodell);

		} catch (ZeitmodellServiceException e) {
			messages.setError(e.getMessage());
			return "zeitmodell/bearbeiten";
		}
		return "redirect:/zeitmodell?showMessage=true";

	}

}
