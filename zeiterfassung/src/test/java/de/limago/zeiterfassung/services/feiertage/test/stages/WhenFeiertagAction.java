package de.limago.zeiterfassung.services.feiertage.test.stages;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.integration.spring.JGivenStage;

import de.limago.zeiterfassung.services.feiertage.Bundesland;
import de.limago.zeiterfassung.services.feiertage.Feiertag;
import de.limago.zeiterfassung.services.feiertage.FeiertagServiceImpl;

@JGivenStage
public class WhenFeiertagAction extends Stage<WhenFeiertagAction>{
	
	@Autowired
	private FeiertagServiceImpl objectUnderTest;

	@ProvidedScenarioState
	private int jahr;
	
	@ProvidedScenarioState
	private Bundesland bundesland;
	
	@ProvidedScenarioState
	private List<Feiertag> feiertage;
	
	public void calculate_holidays() throws Exception{
		feiertage = objectUnderTest.berechneFeiertageFuerJahrUndBundesland(jahr, bundesland);
	}

}
