package de.limago.zeiterfassung.services.feiertage.test.stages;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.annotation.Quoted;
import com.tngtech.jgiven.integration.spring.JGivenStage;

import de.limago.zeiterfassung.services.feiertage.Feiertag;
import javassist.NotFoundException;

@JGivenStage
public class ThenFeiertagOutcome extends Stage<ThenFeiertagOutcome>{

	@ProvidedScenarioState
	private int jahr;
	
	@ProvidedScenarioState
	private List<Feiertag> feiertage;
	
	public void easter_sunday_is_on_$(@Quoted String expected) throws Exception{
		Feiertag ostern = feiertage.stream().filter(f->f.getBezeichnung().equalsIgnoreCase("Ostersonntag")).findFirst().orElseThrow(()->new NotFoundException("Ostern nicht gefunden"));
		assertThat(ostern.getDatum().toString(), equalTo(expected));
	}

	public ThenFeiertagOutcome pentecost_sunday_is_on_$(@Quoted  String expected) throws Exception{
		Feiertag pfingsten = feiertage.stream().filter(f->f.getBezeichnung().equalsIgnoreCase("Pfingstsonntag")).findFirst().orElseThrow(()->new NotFoundException("Pfingsten nicht gefunden"));
		assertThat(pfingsten.getDatum().toString(), equalTo(expected));
		return self();
		
	}
	
	public ThenFeiertagOutcome pentecost_monday_is_on_$(@Quoted  String expected) throws Exception{
		Feiertag Pfingstmontag = feiertage.stream().filter(f->f.getBezeichnung().equalsIgnoreCase("Pfingstmontag")).findFirst().orElseThrow(()->new NotFoundException("Pfingstmontag nicht gefunden"));
		assertThat(Pfingstmontag.getDatum().toString(), equalTo(expected));
		return self();
		
	}

	public ThenFeiertagOutcome good_friday_is_on_$(@Quoted  String expected) throws Exception{
		Feiertag Karfreitag = feiertage.stream().filter(f->f.getBezeichnung().equalsIgnoreCase("Karfreitag")).findFirst().orElseThrow(()->new NotFoundException("Karfreitag nicht gefunden"));
		assertThat(Karfreitag.getDatum().toString(), equalTo(expected));
		return self();
		
	}
	
	public ThenFeiertagOutcome easter_monday_is_on_$(@Quoted  String expected) throws Exception{
		Feiertag Ostermontag = feiertage.stream().filter(f->f.getBezeichnung().equalsIgnoreCase("Ostermontag")).findFirst().orElseThrow(()->new NotFoundException("Ostermontag nicht gefunden"));
		assertThat(Ostermontag.getDatum().toString(), equalTo(expected));
		return self();
		
	}
	
	
	public ThenFeiertagOutcome ascension_is_on_$(@Quoted  String expected) throws Exception{
		Feiertag Himmelfahrt = feiertage.stream().filter(f->f.getBezeichnung().equalsIgnoreCase("Himmelfahrt")).findFirst().orElseThrow(()->new NotFoundException("Himmelfahrt nicht gefunden"));
		assertThat(Himmelfahrt.getDatum().toString(), equalTo(expected));
		return self();
		
	}
	
	
	public ThenFeiertagOutcome corpus_christi_is_on_$(@Quoted  String expected) throws Exception{
		Feiertag Fronleichnam = feiertage.stream().filter(f->f.getBezeichnung().equalsIgnoreCase("Fronleichnam")).findFirst().orElseThrow(()->new NotFoundException("Fronleichnam nicht gefunden"));
		assertThat(Fronleichnam.getDatum().toString(), equalTo(expected));
		return self();
		
	}
	
	public ThenFeiertagOutcome day_of_prayer_and_repentance_is_on_$(@Quoted  String expected) throws Exception{
		Feiertag bub = feiertage.stream().filter(f->f.getBezeichnung().equalsIgnoreCase("Buß- und Bettag")).findFirst().orElseThrow(()->new NotFoundException("Buß- und Bettag nicht gefunden"));
		assertThat(bub.getDatum().toString(), equalTo(expected));
		return self();
		
	}
	
	


}
