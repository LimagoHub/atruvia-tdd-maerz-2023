package de.limago.zeiterfassung.services.feiertage.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.tngtech.jgiven.integration.spring.SpringScenarioTest;

import de.limago.zeiterfassung.services.feiertage.Bundesland;
import de.limago.zeiterfassung.services.feiertage.test.stages.GivenFeiertagState;
import de.limago.zeiterfassung.services.feiertage.test.stages.ThenFeiertagOutcome;
import de.limago.zeiterfassung.services.feiertage.test.stages.WhenFeiertagAction;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FeiertagServiceImplTest
		extends SpringScenarioTest<GivenFeiertagState, WhenFeiertagAction, ThenFeiertagOutcome> {


	@Test
	public void scenario_calculate_easter_2018() throws Exception {
		berechneOsternImpl(2018, "2018-04-01", Bundesland.HE);
	}

	@Test
	public void scenario_calculate_easter_2019() throws Exception {
		berechneOsternImpl(2019, "2019-04-21", Bundesland.HE);
	}

	@Test
	public void scenario_calculate_easter_2020() throws Exception {
		berechneOsternImpl(2020, "2020-04-12", Bundesland.HE);
	}

	@Test
	public void scenario_calculate_easter_2021() throws Exception {
		berechneOsternImpl(2021, "2021-04-04", Bundesland.HE);
	}

	@Test
	public void scenario_calculate_easter_2022() throws Exception {
		berechneOsternImpl(2022, "2022-04-17", Bundesland.HE);
	}

	@Test
	public void scenario_calculate_mobile_holidays_2018_in_Sachsen() throws Exception {
		final Bundesland bundesland = Bundesland.SN;
		given().the_year_$(2018).and().state_is_$(bundesland, bundesland.fullname());
		when().calculate_holidays();
		then()
			.good_friday_is_on_$("2018-03-30")
			.and().easter_monday_is_on_$("2018-04-02")
			.and().pentecost_sunday_is_on_$("2018-05-20")
			.and().pentecost_monday_is_on_$("2018-05-21")
			.and().ascension_is_on_$("2018-05-10")
			.and().day_of_prayer_and_repentance_is_on_$("2018-11-21");
	}

	@Test
	public void scenario_calculate_day_of_prayer_and_repentance_2019_in_Sachsen() throws Exception {
		final Bundesland bundesland = Bundesland.SN;
		given().the_year_$(2019).and().state_is_$(bundesland, bundesland.fullname());
		when().calculate_holidays();
		then().day_of_prayer_and_repentance_is_on_$("2019-11-20");
	}

	@Test
	public void scenario_calculate_day_of_prayer_and_repentance_2020_in_Sachsen() throws Exception {
		final Bundesland bundesland = Bundesland.SN;
		given().the_year_$(2020).and().state_is_$(bundesland, bundesland.fullname());
		when().calculate_holidays();
		then().day_of_prayer_and_repentance_is_on_$("2020-11-18");
	}

	@Test
	public void scenario_calculate_day_of_prayer_and_repentance_2021_in_Sachsen() throws Exception {
		final Bundesland bundesland = Bundesland.SN;
		given().the_year_$(2021).and().state_is_$(bundesland, bundesland.fullname());
		when().calculate_holidays();
		then().day_of_prayer_and_repentance_is_on_$("2021-11-17");
	}

	@Test
	public void scenario_calculate_mobile_holidays_2018_in_Hessen() throws Exception {
		final Bundesland bundesland = Bundesland.HE;
		given().the_year_$(2018).and().state_is_$(bundesland, bundesland.fullname());
		when().calculate_holidays();
		then()
			.good_friday_is_on_$("2018-03-30")
			.and().easter_monday_is_on_$("2018-04-02")
			.and().pentecost_sunday_is_on_$("2018-05-20")
			.and().pentecost_monday_is_on_$("2018-05-21")
			.and().ascension_is_on_$("2018-05-10")
			.and().corpus_christi_is_on_$("2018-05-31");
	}

	
	private void berechneOsternImpl(int jahr, String erwartetesDatum, Bundesland bundesland) throws Exception {
		given().the_year_$(jahr).and().state_is_$(bundesland, bundesland.fullname());
		when().calculate_holidays();
		then().easter_sunday_is_on_$(erwartetesDatum);
	}

}
