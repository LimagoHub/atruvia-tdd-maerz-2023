package de.limago.zeiterfassung.services.mitarbeiter.test.stages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.integration.spring.JGivenStage;

import de.limago.zeiterfassung.repositories.models.Mitarbeiter;
import de.limago.zeiterfassung.services.mitarbeiter.MitarbeiterServiceException;
import de.limago.zeiterfassung.services.mitarbeiter.MitarbeiterServiceImpl;

@Component
@JGivenStage
public class WhenMitarbeiterAction extends Stage<WhenMitarbeiterAction>{
	
	@Autowired
	private MitarbeiterServiceImpl objectUnderTest;
	
	@ProvidedScenarioState
	private Mitarbeiter mitarbeiter;
	
	@ProvidedScenarioState
	private MitarbeiterServiceException mitarbeiterServiceException;

	public void erfasse_neuen_Mitarbeiter() {
		try {
			objectUnderTest.erfasseNeuenMitarbeiter(mitarbeiter);
		} catch (MitarbeiterServiceException e) {
			mitarbeiterServiceException = e;
		}
		
	}

	public void aendere_bestehenden_Mitarbeiter() {
		try {
			objectUnderTest.aendereMitarbeiter(mitarbeiter);
		} catch (MitarbeiterServiceException e) {
			mitarbeiterServiceException = e;
		}
		
	}

}
