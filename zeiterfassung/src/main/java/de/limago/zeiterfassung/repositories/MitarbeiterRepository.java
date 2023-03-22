package de.limago.zeiterfassung.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.limago.zeiterfassung.repositories.models.Mitarbeiter;
import de.limago.zeiterfassung.repositories.models.Zeitmodell;

public interface MitarbeiterRepository extends JpaRepository<Mitarbeiter, String> {

	List<Mitarbeiter> findAllMitarbeiterByNameDescending();
	Optional<Mitarbeiter> findUserWithRolesByUsername(String username);
	List<Mitarbeiter> findMitarbeitertoGivenZeitmodell(Zeitmodell zeitmodell);
	
}
