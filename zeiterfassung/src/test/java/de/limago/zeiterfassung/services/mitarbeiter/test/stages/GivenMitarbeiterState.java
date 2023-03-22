package de.limago.zeiterfassung.services.mitarbeiter.test.stages;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

import org.springframework.stereotype.Component;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.annotation.Quoted;
import com.tngtech.jgiven.integration.spring.JGivenStage;

import de.limago.zeiterfassung.repositories.models.Mitarbeiter;
import de.limago.zeiterfassung.repositories.models.Rolle;
import de.limago.zeiterfassung.repositories.models.Zeitmodell;

@Component
@JGivenStage
public class GivenMitarbeiterState extends Stage<GivenMitarbeiterState>{

	
	@ProvidedScenarioState
	private Mitarbeiter mitarbeiter;
	@ProvidedScenarioState
	private String password;
	
	public void mitarbeiter_is_null() {
		mitarbeiter = null;
		
	}

	public GivenMitarbeiterState new_mitarbeiter_with_username(@Quoted String username) {
		mitarbeiter = createValidMitarbeiter();
		mitarbeiter.setUsername(username); 
		password = mitarbeiter.getPassword();
		return self();
	}
	
	

	public void mitarbeiter_with_password(@Quoted String password) {
		mitarbeiter = createValidMitarbeiter();
		mitarbeiter.setPassword(password);
		
	}

	public void mitarbeiter_with_fullname(@Quoted String fullname) {
		mitarbeiter = createValidMitarbeiter();
		mitarbeiter.setFullname(fullname);
		
	}

	public void mitarbeiter_exists() {
		
		// Ok
	}
	public void mitarbeiter_not_exists() {
		// Ok
		
	}
	

	public GivenMitarbeiterState mitarbeiter_with_Beginn_Arbeitsverhältnis(@Quoted LocalDate localDate) {
		mitarbeiter = createValidMitarbeiter();
		mitarbeiter.setBeginnArbeitverhaeltnis(localDate);
		return self();
	}

	public GivenMitarbeiterState mitarbeiter_with_Ende_Arbeitsverhältnis(@Quoted LocalDate localDate) {
		mitarbeiter = createValidMitarbeiter();
		mitarbeiter.setEndeArbeitverhaeltnis(localDate);
		return self();
	}
	
	public GivenMitarbeiterState Beginn_Arbeitsverhältnis(LocalDate localDate) {
		mitarbeiter.setBeginnArbeitverhaeltnis(localDate);
		return self();
	}

	public GivenMitarbeiterState mitarbeiter_mit_Rolle_$(@Quoted Rolle rolle) {
		mitarbeiter = createValidMitarbeiter();
		mitarbeiter.setUsername("username");
		mitarbeiter.setRollen(new HashSet<Rolle>(Arrays.asList(rolle)));
		return self();
	}

	public GivenMitarbeiterState mitarbeiter_mit_missing_ZeitModell() {
		mitarbeiter.getZeitplaene().clear();
		return self();
	}

	private Mitarbeiter createValidMitarbeiter() {
		Mitarbeiter validMitarbeiter = new Mitarbeiter();
		validMitarbeiter.setUsername("username");
		validMitarbeiter.setPassword("Pa$$w0rd");
		validMitarbeiter.setFullname("John Doe");
		validMitarbeiter.setRollen(new HashSet<>(Arrays.asList(Rolle.MONTEUR)));
		validMitarbeiter.addMonteurZeitmodell(Zeitmodell.builder().id("5c88d4c5-4c8a-45f6-85f0-170ff4112427").bezeichnung("Standard").build());
		return validMitarbeiter;
	}

	

}
