package de.limago.zeiterfassung.services.feiertage.test.stages;

import org.springframework.stereotype.Component;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.Hidden;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.annotation.Quoted;
import com.tngtech.jgiven.integration.spring.JGivenStage;

import de.limago.zeiterfassung.services.feiertage.Bundesland;

@Component
@JGivenStage
public class GivenFeiertagState extends Stage<GivenFeiertagState> {

	
	
	
	@ProvidedScenarioState
	private int jahr;
	
	@ProvidedScenarioState
	private Bundesland bundesland;
	
	public GivenFeiertagState the_year_$(@Quoted int jahr) {
		this.jahr = jahr;
		return self();
	}

	public void state_is_$(@Hidden Bundesland bundesland, @Quoted String name) {
		this.bundesland = bundesland;
		
	}

}
