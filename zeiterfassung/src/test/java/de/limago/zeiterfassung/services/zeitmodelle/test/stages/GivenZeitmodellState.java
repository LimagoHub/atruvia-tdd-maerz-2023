package de.limago.zeiterfassung.services.zeitmodelle.test.stages;

import org.springframework.stereotype.Component;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.annotation.Quoted;
import com.tngtech.jgiven.integration.spring.JGivenStage;

import de.limago.zeiterfassung.repositories.models.Zeitmodell;

@Component
@JGivenStage
public class GivenZeitmodellState extends Stage<GivenZeitmodellState>{

	@ProvidedScenarioState
	private String primaerSchluessel;
	
	@ProvidedScenarioState
	private Zeitmodell zeitmodell;
	
	public GivenZeitmodellState zeitmodell_with_pk_$_exists_in_database(@Quoted String primaerSchluessel) {
		this.primaerSchluessel = primaerSchluessel;
		return self();
	}

	public void some_mitarbeiter_using_this_zeitmodell_exists() {
		// Ok
		
	}

	public void no_mitarbeiter_using_this_zeitmodell_exists() {
		// Ok
		
	}
	
	private Zeitmodell createValidZeitmodell() {
		return Zeitmodell
				.builder()
				.bezeichnung("DummyModell")
				.id("5c88d4c5-4c8a-45f6-85f0-170ff4112420")
				.build();
	}

	public void zeitmodell_is_null() {
		zeitmodell = null;
		
	}

	public GivenZeitmodellState new_Zeiterfassung_with_bezeichnung_$(@Quoted String bezeichnung) {
		zeitmodell = createValidZeitmodell();
		zeitmodell.setBezeichnung(bezeichnung);
		return self();
	}

	public void id_$_is_to_short(@Quoted String id) {
		zeitmodell.setId(id);
		
	}

	public void id_is_$(@Quoted String id) {
		zeitmodell.setId(id);
		primaerSchluessel = id;
		
	}

	public void id_$_is_to_long(@Quoted String id) {
		zeitmodell.setId(id);
		
	}


	public void fritz() {
	}

	public void xyz() {
	}


}
