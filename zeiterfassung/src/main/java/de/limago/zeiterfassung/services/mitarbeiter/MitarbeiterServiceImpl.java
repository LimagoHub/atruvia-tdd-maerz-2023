package de.limago.zeiterfassung.services.mitarbeiter;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.limago.zeiterfassung.repositories.MitarbeiterRepository;
import de.limago.zeiterfassung.repositories.models.Mitarbeiter;
import de.limago.zeiterfassung.repositories.models.Rolle;
 
@Service
@Transactional(rollbackFor = MitarbeiterServiceException.class)
public class MitarbeiterServiceImpl {

	private final Validator validator;

	private final MitarbeiterRepository mitarbeiterRepository;

	public MitarbeiterServiceImpl(final MitarbeiterRepository mitarbeiterRepository, final Validator validator) {
		this.mitarbeiterRepository = mitarbeiterRepository;
		this.validator = validator;
	}

	public List<Mitarbeiter> erzeugeMitarbeiterliste() throws MitarbeiterServiceException {
		try {
			return mitarbeiterRepository.findAllMitarbeiterByNameDescending();
		} catch (RuntimeException e) {
			throw new MitarbeiterServiceException(e);
		}
	}

	public void erfasseNeuenMitarbeiter(final Mitarbeiter mitarbeiter) throws MitarbeiterServiceException {
		try {
			checkMitarbeiter(mitarbeiter);
			verifyThatMitarbeiterNOTExists(mitarbeiter);
			handleNewPassword(mitarbeiter); 
			saveMitarbeiterToDb(mitarbeiter);
		} catch (RuntimeException e) {
			throw new MitarbeiterServiceException(e);
		}

	}

	public void aendereMitarbeiter(final Mitarbeiter mitarbeiter) throws MitarbeiterServiceException {
		try {
			checkMitarbeiter(mitarbeiter);
			verifyThatMitarbeiterExists(mitarbeiter);
			saveMitarbeiterToDb(mitarbeiter);
		} catch (RuntimeException e) {
			throw new MitarbeiterServiceException(e);
		}

	}

	private void checkMitarbeiter(final Mitarbeiter mitarbeiter) throws MitarbeiterServiceException {
		checkIfMitarbeiterIsNull(mitarbeiter);
		checkAttributes(mitarbeiter);
		checkEmploymentTime(mitarbeiter);
		checkMonteurHasZeitmodell(mitarbeiter);
	}

	private void checkMonteurHasZeitmodell(final Mitarbeiter mitarbeiter) throws MitarbeiterServiceException {
		if (mitarbeiter.getRollen().contains(Rolle.MONTEUR) &&  mitarbeiter.getZeitplaene().isEmpty())
			throw new MitarbeiterServiceException("Monteur benötigt Zeitmodell");
	}

	private void checkIfMitarbeiterIsNull(final Mitarbeiter mitarbeiter) throws MitarbeiterServiceException {
		if (mitarbeiter == null)
			throw new MitarbeiterServiceException("Nullwert ist nicht erlaubt");
	}

	private void verifyThatMitarbeiterNOTExists(final Mitarbeiter mitarbeiter) throws MitarbeiterServiceException {
		if (mitarbeiterRepository.existsById(mitarbeiter.getUsername()))
			throw new MitarbeiterServiceException("username bereits vorhanden");
	}

	private void verifyThatMitarbeiterExists(Mitarbeiter mitarbeiter) throws MitarbeiterServiceException {
		if (!mitarbeiterRepository.existsById(mitarbeiter.getUsername()))
			throw new MitarbeiterServiceException("user nicht vorhanden");
	}

	private void validateNewPassword(final Mitarbeiter mitarbeiter) throws MitarbeiterServiceException {
		checkPasswordIsNotEmpty(mitarbeiter);
		checkPasswordPattern(mitarbeiter);
	}

	private void checkPasswordIsNotEmpty(final Mitarbeiter mitarbeiter) throws MitarbeiterServiceException {
		if (mitarbeiter.getPassword() == null)
			throw new MitarbeiterServiceException("Password darf nicht null sein.");
	}

	private void checkPasswordPattern(final Mitarbeiter mitarbeiter) throws MitarbeiterServiceException {
		Pattern p = Pattern.compile("^((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,255})$");
		Matcher m = p.matcher(mitarbeiter.getPassword());
		if (!m.matches())
			throw new MitarbeiterServiceException(
					"Password braucht min. 6 Stellen, muss min. einen Gross- und einen Kleinbuchstaben, sowie eine Ziffer und eines dieser Zeichen '@#$%' enthalten.");
	}

	private void checkAttributes(final Mitarbeiter mitarbeiter) throws MitarbeiterServiceException {
		final Set<ConstraintViolation<Mitarbeiter>> violations = validator.validate(mitarbeiter);
		if (!violations.isEmpty()) {
			throw new MitarbeiterServiceException(violations.stream().findFirst()
					.orElseThrow(() -> new ConstraintViolationException(violations)).getMessage());
		}
	}

	private void checkEmploymentTime(final Mitarbeiter mitarbeiter) throws MitarbeiterServiceException {
		if (mitarbeiter.getEndeArbeitverhaeltnis().isBefore(mitarbeiter.getBeginnArbeitverhaeltnis()))
			throw new MitarbeiterServiceException("Ende Arbeitsverhältnis liegt vor Beginn Arbeitsverhältnis");
	}

	private void handleNewPassword(final Mitarbeiter mitarbeiter) throws MitarbeiterServiceException {
		validateNewPassword(mitarbeiter);
		encryptPassword(mitarbeiter);
	}

	private void encryptPassword(final Mitarbeiter mitarbeiter) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(mitarbeiter.getPassword());
		mitarbeiter.setPassword(hashedPassword);
	}

	private void saveMitarbeiterToDb(final Mitarbeiter mitarbeiter) {
		mitarbeiterRepository.save(mitarbeiter);
	}
}