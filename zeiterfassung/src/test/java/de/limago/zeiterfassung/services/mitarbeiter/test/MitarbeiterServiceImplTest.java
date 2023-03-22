package de.limago.zeiterfassung.services.mitarbeiter.test;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.tngtech.jgiven.integration.spring.SpringScenarioTest;

import de.limago.zeiterfassung.repositories.models.Rolle;
import de.limago.zeiterfassung.services.mitarbeiter.test.stages.GivenMitarbeiterState;
import de.limago.zeiterfassung.services.mitarbeiter.test.stages.ThenMitarbeiterOutcome;
import de.limago.zeiterfassung.services.mitarbeiter.test.stages.WhenMitarbeiterAction;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql({"/create.sql","/insert.sql"})
public class MitarbeiterServiceImplTest extends SpringScenarioTest<GivenMitarbeiterState, WhenMitarbeiterAction, ThenMitarbeiterOutcome>{

	//@MockBean private MitarbeiterRepository mitarbeiterRepositoryMock;
	
	
	@Test 
	public void Scenario_save_Mitarbeiter_isNull() {
		given().mitarbeiter_is_null();
		when().erfasse_neuen_Mitarbeiter();
		then().expected_MitarbeiterServiceException_is_thrown()
		.and().expected_errormessage_is_$("Nullwert ist nicht erlaubt");
		 
	}

	@Test
	public void Scenario_save_Mitarbeiter_and_mitarbeiter_exists() {
		given().new_mitarbeiter_with_username("peterhinz").
		and().mitarbeiter_exists();
		when().erfasse_neuen_Mitarbeiter();
		then().expected_MitarbeiterServiceException_is_thrown()
		.and().expected_errormessage_is_$("username bereits vorhanden");
		
	}

	@Test
	public void Scenario_save_Mitarbeiter_and_Username_is_toShort() {
		given().new_mitarbeiter_with_username("Peter");
		when().erfasse_neuen_Mitarbeiter();
		then().expected_MitarbeiterServiceException_is_thrown()
		.and().expected_errormessage_is_$("username braucht min. 6 Stellen, nur Buchstaben und Ziffern sind erlaubt.");
		
	}

	@Test
	public void Scenario_save_Mitarbeiter_and_Fullname_is_to_short() {
		given().mitarbeiter_with_fullname("p");
		when().erfasse_neuen_Mitarbeiter();
		then().expected_MitarbeiterServiceException_is_thrown()
		.and().expected_errormessage_is_$("muss zwischen 2 und 50 liegen");
		
	}

	@Test
	public void Scenario_save_Mitarbeiter_and_Fullname_is_to_long() {
		given().mitarbeiter_with_fullname("01234567890123456789012345678901234567890123456789X");
		when().erfasse_neuen_Mitarbeiter();
		then().expected_MitarbeiterServiceException_is_thrown()
		.and().expected_errormessage_is_$("muss zwischen 2 und 50 liegen");
		
		
	}

	@Test
	public void Scenario_save_Mitarbeiter_and_Password_is_not_valid() {
		
		given().mitarbeiter_with_password("geheim");
		when().erfasse_neuen_Mitarbeiter();
		then().expected_MitarbeiterServiceException_is_thrown()
		.and().expected_errormessage_is_$("Password braucht min. 6 Stellen, muss min. einen Gross- und einen Kleinbuchstaben, sowie eine Ziffer und eines dieser Zeichen '@#$%' enthalten.");
		
	}

	@Test
	public void Scenario_save_Mitarbeiter_and_Password_is_null() {
		
		given().mitarbeiter_with_password(null);
		when().erfasse_neuen_Mitarbeiter();
		then().expected_MitarbeiterServiceException_is_thrown()
		.and().expected_errormessage_is_$("Password darf nicht leer sein.");
		
	}
	
	@Test
	public void Scenario_save_Mitarbeiter_and_Beginn_Arbeitsverhältnis_is_null() {
		
		given().mitarbeiter_with_Beginn_Arbeitsverhältnis(null);
		when().erfasse_neuen_Mitarbeiter();
		then().expected_MitarbeiterServiceException_is_thrown()
		.and().expected_errormessage_is_$("Beginn Arbeitsverhältnis darf nicht null sein.");
		
	}
	
	@Test
	public void Scenario_save_Mitarbeiter_and_Ende_Arbeitsverhältnis_is_null() {
		
		given().mitarbeiter_with_Ende_Arbeitsverhältnis(null);
		when().erfasse_neuen_Mitarbeiter();
		then().expected_MitarbeiterServiceException_is_thrown()
		.and().expected_errormessage_is_$("Ende Arbeitsverhältnis darf nicht null sein.");
		
	}
	
	@Test
	public void Scenario_save_Mitarbeiter_and_Ende_Arbeitsverhältnis_is_after_Beginn_Arbeitsverhältnis() {
		
		given().mitarbeiter_with_Ende_Arbeitsverhältnis(LocalDate.parse("2019-05-21")).and().Beginn_Arbeitsverhältnis(LocalDate.parse("2020-05-21"));
		when().erfasse_neuen_Mitarbeiter();
		then().expected_MitarbeiterServiceException_is_thrown()
		.and().expected_errormessage_is_$("Ende Arbeitsverhältnis liegt vor Beginn Arbeitsverhältnis");
		
	}
	
	@Test
	public void Scenario_save_Mitarbeiter_mit_Rolle_Monteur_and_missing_ZeitModell() {
		
		given().mitarbeiter_mit_Rolle_$(Rolle.MONTEUR).and().mitarbeiter_mit_missing_ZeitModell();
		when().erfasse_neuen_Mitarbeiter();
		then().expected_MitarbeiterServiceException_is_thrown()
		.and().expected_errormessage_is_$("Monteur benötigt Zeitmodell");
		
	}
	
	@Test
	public void Scenario_save_Mitarbeiter_successfully_saved_in_database_with_hashed_password() {
		
		given().new_mitarbeiter_with_username("karlheinz");
		when().erfasse_neuen_Mitarbeiter();
		then().Mitarbeiter_is_succesfully_saved_in_db()
		  .and().his_password_in_db_is_hashed();
		
	}

	
	// ########################
	
	@Test
	public void Scenario_update_Mitarbeiter_isNull() {
		given().mitarbeiter_is_null();
		when().aendere_bestehenden_Mitarbeiter();
		then().expected_MitarbeiterServiceException_is_thrown()
		.and().expected_errormessage_is_$("Nullwert ist nicht erlaubt");
		
	}

	@Test
	public void Scenario_update_Mitarbeiter_and_mitarbeiter_do_not_exists() {
		given().new_mitarbeiter_with_username("fritzmayer").
		and().mitarbeiter_not_exists();
		when().aendere_bestehenden_Mitarbeiter();
		then().expected_MitarbeiterServiceException_is_thrown()
		.and().expected_errormessage_is_$("user nicht vorhanden");
		
	}

	@Test
	public void Scenario_update_Mitarbeiter_and_Username_is_toShort() {
		given().new_mitarbeiter_with_username("Peter");
		when().aendere_bestehenden_Mitarbeiter();
		then().expected_MitarbeiterServiceException_is_thrown()
		.and().expected_errormessage_is_$("username braucht min. 6 Stellen, nur Buchstaben und Ziffern sind erlaubt.");
		
	}

	@Test
	public void Scenario_update_Mitarbeiter_and_Fullname_is_to_short() {
		given().mitarbeiter_with_fullname("p");
		when().aendere_bestehenden_Mitarbeiter();
		then().expected_MitarbeiterServiceException_is_thrown()
		.and().expected_errormessage_is_$("muss zwischen 2 und 50 liegen");
		
	}

	@Test
	public void Scenario_update_Mitarbeiter_and_Fullname_is_to_long() {
		given().mitarbeiter_with_fullname("01234567890123456789012345678901234567890123456789X");
		when().aendere_bestehenden_Mitarbeiter();
		then().expected_MitarbeiterServiceException_is_thrown()
		.and().expected_errormessage_is_$("muss zwischen 2 und 50 liegen");
		
		
	}

	@Test
	public void Scenario_update_Mitarbeiter_and_Password_is_null() {
		
		given().mitarbeiter_with_password(null);
		when().aendere_bestehenden_Mitarbeiter();
		then().expected_MitarbeiterServiceException_is_thrown()
		.and().expected_errormessage_is_$("Password darf nicht leer sein.");
		
	}
	
	@Test
	public void Scenario_update_Mitarbeiter_and_Beginn_Arbeitsverhältnis_is_null() {
		
		given().mitarbeiter_with_Beginn_Arbeitsverhältnis(null);
		when().aendere_bestehenden_Mitarbeiter();
		then().expected_MitarbeiterServiceException_is_thrown()
		.and().expected_errormessage_is_$("Beginn Arbeitsverhältnis darf nicht null sein.");
		
	}
	
	@Test
	public void Scenario_update_Mitarbeiter_and_Ende_Arbeitsverhältnis_is_null() {
		
		given().mitarbeiter_with_Ende_Arbeitsverhältnis(null);
		when().aendere_bestehenden_Mitarbeiter();
		then().expected_MitarbeiterServiceException_is_thrown()
		.and().expected_errormessage_is_$("Ende Arbeitsverhältnis darf nicht null sein.");
		
	}
	
	@Test
	public void Scenario_update_Mitarbeiter_and_Ende_Arbeitsverhältnis_is_after_Beginn_Arbeitsverhältnis() {
		
		given().mitarbeiter_with_Ende_Arbeitsverhältnis(LocalDate.parse("2019-05-21")).and().Beginn_Arbeitsverhältnis(LocalDate.parse("2020-05-21"));
		when().aendere_bestehenden_Mitarbeiter();
		then().expected_MitarbeiterServiceException_is_thrown()
		.and().expected_errormessage_is_$("Ende Arbeitsverhältnis liegt vor Beginn Arbeitsverhältnis");
		
	}
	
	@Test
	public void Scenario_update_Mitarbeiter_mit_Rolle_Monteur_and_missing_ZeitModell() {
		
		given().mitarbeiter_mit_Rolle_$(Rolle.MONTEUR).and().mitarbeiter_mit_missing_ZeitModell();
		when().aendere_bestehenden_Mitarbeiter();
//		then().expected_MitarbeiterServiceException_is_thrown()
//		.and().expected_errormessage_is_$("Monteur benötigt Zeitmodell");
//		
	} 
	
	@Test
	public void Scenario_update_Mitarbeiter_successfully_saved_in_database_with_hashed_password() {
		
		given().new_mitarbeiter_with_username("peterhinz");
		when().aendere_bestehenden_Mitarbeiter();
		then().Mitarbeiter_is_succesfully_saved_in_db();
		  
		
	}

}
