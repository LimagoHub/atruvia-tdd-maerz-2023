package de.limago.zeiterfassung.services.zeitmodelle;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.limago.zeiterfassung.repositories.MitarbeiterRepository;
import de.limago.zeiterfassung.repositories.ZeitmodellRepository;
import de.limago.zeiterfassung.repositories.models.Mitarbeiter;
import de.limago.zeiterfassung.repositories.models.Zeitmodell;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(rollbackFor = ZeitmodellServiceException.class)
@Slf4j
public class ZeitmodellServiceImpl implements ZeitmodellService {

	private final MitarbeiterRepository mitarbeiterRepository;
	private final ZeitmodellRepository zeitmodellRepository;
	private final Validator validator;

	public ZeitmodellServiceImpl(final MitarbeiterRepository mitarbeiterRepository,
			final ZeitmodellRepository zeitmodellRepository, final Validator validator) {
		this.mitarbeiterRepository = mitarbeiterRepository;
		this.zeitmodellRepository = zeitmodellRepository;
		this.validator = validator;
	}

	@Override
	public void speichereOderAendereZeitmodell(final Zeitmodell zeitmodell) throws ZeitmodellServiceException {
		try {
			checkNullValue(zeitmodell);
			checkAttributes(zeitmodell);
			checkUsing(zeitmodell);
			saveOrUpdate(zeitmodell);
		} catch (RuntimeException e) {
			log.error("Fehler beim Speichern des Zeitmodells", e);
			throw new ZeitmodellServiceException("Fehler beim Speichern des Zeitmodells", e);
		}
	}

	@Override
	public void loescheZeitmodell(final Zeitmodell zeitmodell) throws ZeitmodellServiceException {
		loescheZeitmodell(zeitmodell.getId());
	}

	@Override
	public void loescheZeitmodell(final String id) throws ZeitmodellServiceException {
		try {
			Optional<Zeitmodell> optional = zeitmodellRepository.findById(id);
			if (optional.isPresent()) {
				checkUsing(optional.get());
				zeitmodellRepository.deleteById(id);
			}
		} catch (RuntimeException e) {
			log.error("Fehler beim Löschen des Zeitmodells", e);
			throw new ZeitmodellServiceException("Fehler beim Löschen des Zeitmodells", e);
		}
	}
	
	@Override
	public boolean isZeitmodellActive(final Zeitmodell zeitmodell) throws ZeitmodellServiceException{
		try {
			return !mitarbeiterRepository.findMitarbeitertoGivenZeitmodell(zeitmodell).isEmpty();
			
		} catch (RuntimeException e) {
			log.error("Fehler bei der Activitätsprüfung", e);
			throw new ZeitmodellServiceException("Fehler bei der Activitätsprüfung", e);
		}
	}

	@Override
	public List<Mitarbeiter> findeMitarbeiterZuZeitmodell(final Zeitmodell zeitmodell)
			throws ZeitmodellServiceException {
		try {
			return mitarbeiterRepository.findMitarbeitertoGivenZeitmodell(zeitmodell);
		} catch (RuntimeException e) {
			log.error("Fehler beim Suchen nach Mitarbeitern", e);
			throw new ZeitmodellServiceException("Fehler beim Suchen nach Mitarbeitern", e);
		}
	}

	@Override
	public List<Zeitmodell> findeAlleZeitmodelle() throws ZeitmodellServiceException {
		try {
			return zeitmodellRepository.findAllZeitmodelle();
		} catch (RuntimeException e) {
			log.error("Fehler beim Suchen nach Zeitmodellen", e);
			throw new ZeitmodellServiceException("Fehler beim Suchen nach Zeitmodellen", e);
		}
	}

	@Override
	public Optional<Zeitmodell> findeZeitModellNachPrimaerSchluessel(final String primaerSchluessel)
			throws ZeitmodellServiceException {
		try {
			return zeitmodellRepository.findById(primaerSchluessel);
		} catch (RuntimeException e) {
			log.error("Fehler beim Suchen nach einem Zeitmodell", e);
			throw new ZeitmodellServiceException("Fehler beim Suchen nach einem Zeitmodell", e);
		}
	}

	private void checkNullValue(final Zeitmodell zeitmodell) throws ZeitmodellServiceException {
		if (zeitmodell == null)
			throw new ZeitmodellServiceException("Nullwert ist nicht erlaubt");
	}

	private void checkAttributes(final Zeitmodell zeitmodell) throws ZeitmodellServiceException {
		final Set<ConstraintViolation<Zeitmodell>> violations = validator.validate(zeitmodell);
		if (!violations.isEmpty()) {
			throw new ZeitmodellServiceException(violations.stream().findFirst()
					.orElseThrow(() -> new ConstraintViolationException(violations)).getMessage());
		}
	}

	private void saveOrUpdate(final Zeitmodell zeitmodell) {
		zeitmodellRepository.save(zeitmodell);
	}

	private void checkUsing(final Zeitmodell zeitmodell) throws ZeitmodellServiceException {
		if (isZeitmodellActive(zeitmodell))
			throw new ZeitmodellServiceException(
					"Dieses Zeitmodell wird noch verwendet und darf deswegen weder gelöscht noch geändert werden.");
	}

}
