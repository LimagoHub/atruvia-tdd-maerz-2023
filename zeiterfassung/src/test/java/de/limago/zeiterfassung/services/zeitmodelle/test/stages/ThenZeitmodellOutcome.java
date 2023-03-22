package de.limago.zeiterfassung.services.zeitmodelle.test.stages;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.annotation.Quoted;
import com.tngtech.jgiven.integration.spring.JGivenStage;

import de.limago.zeiterfassung.repositories.ZeitmodellRepository;
import de.limago.zeiterfassung.repositories.models.Mitarbeiter;
import de.limago.zeiterfassung.repositories.models.Zeitmodell;
import de.limago.zeiterfassung.services.zeitmodelle.ZeitmodellServiceException;

@Component
@JGivenStage
public class ThenZeitmodellOutcome extends Stage<ThenZeitmodellOutcome>{
	
	@Autowired
	private ZeitmodellRepository zeitmodellRepository;

	
	@ProvidedScenarioState
	private String primaerSchluessel;
	
	@ProvidedScenarioState
	private Optional<Zeitmodell> zeitmodellOptional;
	
	@ProvidedScenarioState
	private ZeitmodellServiceException zeitmodellServiceException;

	@ProvidedScenarioState
	private List<Zeitmodell> zeitmodelle;
	
	@ProvidedScenarioState
	private List<Mitarbeiter> mitarbeiter;

	
	public void zeitmodell_was_loaded_from_database() {
		assertThat(zeitmodellServiceException, equalTo(null));
		assertThat(zeitmodellOptional.isPresent(), equalTo(true));
		assertThat(zeitmodellOptional.get().getBezeichnung(), equalTo("Standard"));
		assertThat(zeitmodellOptional.get().getId(), equalTo(primaerSchluessel));
	}


	public void all_zeitmodelle_where_loaded_from_database() {
		assertThat(zeitmodellServiceException, equalTo(null));
		assertThat(zeitmodelle.size(), equalTo(2));
		assertThat(zeitmodelle.stream().filter(z->z.getId().equals("5c88d4c5-4c8a-45f6-85f0-170ff4112427")).count(), equalTo(1L));
		assertThat(zeitmodelle.stream().filter(z->z.getId().equals("5c88d4c5-4c8a-45f6-85f0-170ff4112428")).count(), equalTo(1L));
		
	}


	public void find_all_mitarbeiter_to_zeitmodell() {
		assertThat(zeitmodellServiceException, equalTo(null));
		assertThat(mitarbeiter.size(), equalTo(2));
		assertThat(mitarbeiter.stream().filter(z->z.getUsername().equals("peterhinz")).count(), equalTo(1L));
		assertThat(mitarbeiter.stream().filter(z->z.getUsername().equals("limago")).count(), equalTo(1L));
		
	}

	

	public void expected_errormessage_is_$(@Quoted String message) {
		assertThat( zeitmodellServiceException.getMessage(), equalTo(message));
	}

	public ThenZeitmodellOutcome expected_ZeitmodellServiceException_is_thrown() {
		
		assertThat( zeitmodellServiceException, not(equalTo(null)));
		
		return self();
	}


	public void zeitmodell_is_deleted_in_database() {
		assertThat(zeitmodellServiceException, equalTo(null));
		assertThat(zeitmodellRepository.existsById(primaerSchluessel), equalTo(false));
		
	}


	public void zeitmodell_is_saved_in_db() {
		assertThat(zeitmodellServiceException, equalTo(null));
		assertThat(zeitmodellRepository.existsById(primaerSchluessel), equalTo(true));
	}

}
