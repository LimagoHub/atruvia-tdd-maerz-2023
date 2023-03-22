package de.limago.zeiterfassung.services.zeitmodelle.test.stages;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.integration.spring.JGivenStage;

import de.limago.zeiterfassung.repositories.models.Mitarbeiter;
import de.limago.zeiterfassung.repositories.models.Zeitmodell;
import de.limago.zeiterfassung.services.zeitmodelle.ZeitmodellService;
import de.limago.zeiterfassung.services.zeitmodelle.ZeitmodellServiceException;

@Component
@JGivenStage
public class WhenZeitmodellAction extends Stage<WhenZeitmodellAction> {

	@Autowired
	private ZeitmodellService objectUnderTest;

	@ProvidedScenarioState
	private String primaerSchluessel;

	@ProvidedScenarioState
	private Optional<Zeitmodell> zeitmodellOptional;

	@ProvidedScenarioState
	private ZeitmodellServiceException zeitmodellServiceException;

	@ProvidedScenarioState
	private List<Zeitmodell> zeitmodelle;

	@ProvidedScenarioState
	private Zeitmodell zeitmodell;

	
	@ProvidedScenarioState
	private List<Mitarbeiter> mitarbeiter;

	public void find_zeitmodell_by_pk() {
		try {
			zeitmodellOptional = objectUnderTest.findeZeitModellNachPrimaerSchluessel(primaerSchluessel);
		} catch (ZeitmodellServiceException e) {
			zeitmodellServiceException = e;
		}

	}

	public void find_all_zeitmodelle() {
		try {
			zeitmodelle = objectUnderTest.findeAlleZeitmodelle();
		} catch (ZeitmodellServiceException e) {
			zeitmodellServiceException = e;
		}

	}

	public void find_mitarbeiter_by_zeitmodell() {
		try {
			zeitmodellOptional = objectUnderTest.findeZeitModellNachPrimaerSchluessel(primaerSchluessel);
			mitarbeiter = objectUnderTest
					.findeMitarbeiterZuZeitmodell(zeitmodellOptional.orElseThrow(() -> new RuntimeException("Upps")));
		} catch (ZeitmodellServiceException e) {
			zeitmodellServiceException = e;
		}

	}

	public void delete_zeitmodell() {
		try {

			objectUnderTest.loescheZeitmodell(primaerSchluessel);
		} catch (ZeitmodellServiceException e) {
			zeitmodellServiceException = e;
		}

	}

	public void save_Zeitmodell() {
		try {
			objectUnderTest.speichereOderAendereZeitmodell(zeitmodell);
		} catch (ZeitmodellServiceException e) {
			zeitmodellServiceException = e;
		}

	}

}
