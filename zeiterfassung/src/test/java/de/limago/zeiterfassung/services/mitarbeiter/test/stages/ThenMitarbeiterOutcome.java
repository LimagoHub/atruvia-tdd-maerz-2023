package de.limago.zeiterfassung.services.mitarbeiter.test.stages;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.annotation.Quoted;
import com.tngtech.jgiven.integration.spring.JGivenStage;

import de.limago.zeiterfassung.repositories.MitarbeiterRepository;
import de.limago.zeiterfassung.repositories.models.Mitarbeiter;
import de.limago.zeiterfassung.services.mitarbeiter.MitarbeiterServiceException;

@Component
@JGivenStage
public class ThenMitarbeiterOutcome extends Stage<ThenMitarbeiterOutcome>{
	
	@Autowired
	private MitarbeiterRepository mitarbeiterRepository;
	
	@ProvidedScenarioState
	private MitarbeiterServiceException mitarbeiterServiceException;
	@ProvidedScenarioState
	private Mitarbeiter mitarbeiter;
	@ProvidedScenarioState
	private String password;

	public Stage<ThenMitarbeiterOutcome> expected_MitarbeiterServiceException_is_thrown() {
		assertThat( mitarbeiterServiceException, not(equalTo(null)));
	
		return self();
	}

	public void expected_errormessage_is_$(@Quoted String message) {
		assertThat( mitarbeiterServiceException.getMessage(), equalTo(message));
	}

	public ThenMitarbeiterOutcome Mitarbeiter_is_succesfully_saved_in_db() {
		assertThat(mitarbeiterServiceException, equalTo(null));
		assertThat(mitarbeiterRepository.existsById(mitarbeiter.getUsername()), equalTo(true));
		return self(); 
	}

	public void his_password_in_db_is_hashed() {
		assertThat(new BCryptPasswordEncoder().matches(password, mitarbeiterRepository.findById(mitarbeiter.getUsername()).get().getPassword()), equalTo(true));
		
	}

}
