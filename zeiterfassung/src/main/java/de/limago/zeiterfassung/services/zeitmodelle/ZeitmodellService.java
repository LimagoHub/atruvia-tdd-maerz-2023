package de.limago.zeiterfassung.services.zeitmodelle;

import java.util.List;
import java.util.Optional;

import de.limago.zeiterfassung.repositories.models.Mitarbeiter;
import de.limago.zeiterfassung.repositories.models.Zeitmodell;

public interface ZeitmodellService {

	void speichereOderAendereZeitmodell(Zeitmodell zeitmodell) throws ZeitmodellServiceException;

	void loescheZeitmodell(Zeitmodell zeitmodell) throws ZeitmodellServiceException;

	void loescheZeitmodell(String id) throws ZeitmodellServiceException;

	boolean isZeitmodellActive(Zeitmodell zeitmodell) throws ZeitmodellServiceException;

	List<Mitarbeiter> findeMitarbeiterZuZeitmodell(Zeitmodell zeitmodell) throws ZeitmodellServiceException;

	List<Zeitmodell> findeAlleZeitmodelle() throws ZeitmodellServiceException;

	Optional<Zeitmodell> findeZeitModellNachPrimaerSchluessel(String primaerSchluessel)
			throws ZeitmodellServiceException;

}