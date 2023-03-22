package de.limago.zeiterfassung.services.zeitmodelle.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.tngtech.jgiven.integration.spring.SpringScenarioTest;

import de.limago.zeiterfassung.services.zeitmodelle.test.stages.GivenZeitmodellState;
import de.limago.zeiterfassung.services.zeitmodelle.test.stages.ThenZeitmodellOutcome;
import de.limago.zeiterfassung.services.zeitmodelle.test.stages.WhenZeitmodellAction;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql({"/create.sql","/insert.sql"})
public class ZeitmodellServiceImplTest extends SpringScenarioTest<GivenZeitmodellState, WhenZeitmodellAction, ThenZeitmodellOutcome>{

	@Test
	public void scenario_find_zeitmodell_mit_primaerschluessel() {
		
		given().zeitmodell_with_pk_$_exists_in_database("5c88d4c5-4c8a-45f6-85f0-170ff4112427");
		when().find_zeitmodell_by_pk();
		then().zeitmodell_was_loaded_from_database();
	}
	@Test
	public void scenario_find_all_zeitmodelle() {
		when().find_all_zeitmodelle();
		then().all_zeitmodelle_where_loaded_from_database();
	}
	
	@Test
	public void scenario_find_mitarbeiter_by_zeitmodell() {
		given().zeitmodell_with_pk_$_exists_in_database("5c88d4c5-4c8a-45f6-85f0-170ff4112427");
		when().find_mitarbeiter_by_zeitmodell();
		then().find_all_mitarbeiter_to_zeitmodell();
	}
	@Test
	public void scenario_delete_zeitmodell_should_fail_if_active() {
		
		given().zeitmodell_with_pk_$_exists_in_database("5c88d4c5-4c8a-45f6-85f0-170ff4112427")
		.and().some_mitarbeiter_using_this_zeitmodell_exists();
		when().delete_zeitmodell();
		then().expected_ZeitmodellServiceException_is_thrown()
		.and().expected_errormessage_is_$("Dieses Zeitmodell wird noch verwendet und darf deswegen weder gelöscht noch geändert werden.");
	}
	@Test
	public void scenario_delete_zeitmodell_should_succeed_if_not_active() {
		given().zeitmodell_with_pk_$_exists_in_database("5c88d4c5-4c8a-45f6-85f0-170ff4112428")
		.and().no_mitarbeiter_using_this_zeitmodell_exists();
		when().delete_zeitmodell();
		then().zeitmodell_is_deleted_in_database();
		
	}
	
	// #########################
	
	
	@Test 
	public void Scenario_save_Zeitmodell_isNull() {
		given().zeitmodell_is_null();
		when().save_Zeitmodell();
		then().expected_ZeitmodellServiceException_is_thrown()
		.and().expected_errormessage_is_$("Nullwert ist nicht erlaubt");
		 
	}

	@Test 
	public void Scenario_save_Zeitmodell_ID_isNull() {
		given().new_Zeiterfassung_with_bezeichnung_$("DummyModell").and().id_is_$(null);
			when().save_Zeitmodell();
		then().expected_ZeitmodellServiceException_is_thrown()
		.and().expected_errormessage_is_$("ID darf nicht leer sein.");
		 
	}

	@Test
	public void Scenario_save_Zeitmodell_and_id_is_toShort() {
		given().new_Zeiterfassung_with_bezeichnung_$("DummyModell").and().id_$_is_to_short("012345678901234567890123456789");
		when().save_Zeitmodell();
		then().expected_ZeitmodellServiceException_is_thrown()
		.and().expected_errormessage_is_$("Id muss genau 36 Zeichen lang sein");
		
	}

	@Test
	public void Scenario_save_Zeitmodell_and_id_is_to_long() {
		given().new_Zeiterfassung_with_bezeichnung_$("DummyModell").and().id_$_is_to_long("0123456789012345678901234567890123456789");
		when().save_Zeitmodell();
		then().expected_ZeitmodellServiceException_is_thrown()
		.and().expected_errormessage_is_$("Id muss genau 36 Zeichen lang sein");
		
	}


	@Test
	public void Scenario_save_Zeitmodell_and_Bezeichnung_is_to_short() {
		given().new_Zeiterfassung_with_bezeichnung_$("01234");
		when().save_Zeitmodell();
		then().expected_ZeitmodellServiceException_is_thrown()
		.and().expected_errormessage_is_$("Bezeichnung muss mindestens 6 und maximal 255 Buchstaben haben.");
		
	}

	@Test
	public void Scenario_save_Zeitmodell_and_Bezeichnung_is_to_long() {
		given().new_Zeiterfassung_with_bezeichnung_$("01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
		when().save_Zeitmodell();
		then().expected_ZeitmodellServiceException_is_thrown()
		.and().expected_errormessage_is_$("Bezeichnung muss mindestens 6 und maximal 255 Buchstaben haben.");
		
	}


	@Test
	public void Scenario_save_active_Zeitmodell_should_fail() {
		given().new_Zeiterfassung_with_bezeichnung_$("DummyModell").and().id_is_$("5c88d4c5-4c8a-45f6-85f0-170ff4112427");
		when().save_Zeitmodell();
		then().expected_ZeitmodellServiceException_is_thrown()
		.and().expected_errormessage_is_$("Dieses Zeitmodell wird noch verwendet und darf deswegen weder gelöscht noch geändert werden.");
		
	}

	@Test
	public void Scenario_save_non_active_Zeitmodell_should_succeed() {
		given().new_Zeiterfassung_with_bezeichnung_$("DummyModell").and().id_is_$("5c88d4c5-4c8a-45f6-85f0-170ff4112420");
		when().save_Zeitmodell();
		then().zeitmodell_is_saved_in_db();
	}

	

}
