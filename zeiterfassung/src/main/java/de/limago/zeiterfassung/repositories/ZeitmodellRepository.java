package de.limago.zeiterfassung.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.limago.zeiterfassung.repositories.models.Zeitmodell;

public interface ZeitmodellRepository extends JpaRepository<Zeitmodell, String> {
	List<Zeitmodell> findAllZeitmodelle();
}
