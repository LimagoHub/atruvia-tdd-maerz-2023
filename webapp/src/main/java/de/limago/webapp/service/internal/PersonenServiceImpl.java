package de.limago.webapp.service.internal;

import de.limago.webapp.repository.PersonenRepository;
import de.limago.webapp.repository.entities.PersonEntity;
import de.limago.webapp.service.BlacklistService;
import de.limago.webapp.service.PersonenService;
import de.limago.webapp.service.PersonenServiceException;
import de.limago.webapp.service.mapper.PersonMapper;
import de.limago.webapp.service.model.Person;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
@Transactional(rollbackFor = PersonenServiceException.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class PersonenServiceImpl implements PersonenService {

    private final PersonenRepository repo;
    private final PersonMapper mapper;
    private final BlacklistService blacklistService;

/*
        person ist null => PSE
        vorname ist null vorname kürzer 2 Zeichen =>PSE
        nachname ist null nachname kürzer 2 Zeichen =>PSE
        Vorname Attila =>PSE
        repo-Exception =>PSE
        Person an Repo uebergeben

     */

    @Override
    public void speichern(Person person) throws PersonenServiceException {
        try {
            //if(repo.existsById(person.getId())) throw new PersonenServiceException("Schon vorhanden");
            speichernImpl(person);
        } catch (RuntimeException e) { // Immer auf Runtimeexception checken
            throw new PersonenServiceException("Interner Fehler", e);
        }
    }

    private void speichernImpl(Person person) throws PersonenServiceException {
        checkPerson(person);
        repo.save(mapper.convert(person));
    }

    private void checkPerson(Person person) throws PersonenServiceException {

        validate(person);
        businessCheck(person);
    }

    private void businessCheck(Person person) throws PersonenServiceException {
        if(blacklistService.isBlacklisted(person))
            throw new PersonenServiceException("Unerwuenschte Person");
    }

    private static void validate(Person person) throws PersonenServiceException {
        if(person == null) // Immer auf NUll pruefen
            throw new PersonenServiceException("Person darf nicht null sein");
        if(person.getVorname() == null || person.getVorname().length() < 2)
            throw new PersonenServiceException("Vorname zu kurz");
        if(person.getNachname() == null || person.getNachname().length() < 2)
            throw new PersonenServiceException("Nachname zu kurz");
    }

    @Override
    public void aendern(Person person) throws PersonenServiceException {
        try {
            //if(! repo.existsById(person.getId())) throw new PersonenServiceException("Nicht vorhanden");
            checkPerson(person);
            repo.save(mapper.convert(person));
        } catch (RuntimeException e) {
            throw new PersonenServiceException("Interner Fehler", e);
        }
    }

    @Override
    public boolean loesche(String id) throws PersonenServiceException {
        try {
            if(repo.existsById(id)) {
                repo.deleteById(id);
                return true;
            }
            return false;
        } catch (RuntimeException e) {
            throw new PersonenServiceException(e);
        }
    }



    // Select * from customers with ur
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Override
    public Optional<Person> findeNachId(String id) throws PersonenServiceException {
        try {
            return repo.findById(id).map(mapper::convert);
        } catch (RuntimeException e) {
            throw new PersonenServiceException(e);
        }
    }

    @Override
    public Iterable<Person> findeAlle() throws PersonenServiceException {
        try {
            return mapper.convert(repo.findAll());
        } catch (RuntimeException e) {
            throw new PersonenServiceException(e);
        }
    }


}
