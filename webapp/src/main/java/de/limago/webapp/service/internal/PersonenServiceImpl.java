package de.limago.webapp.service.internal;

import de.limago.webapp.repository.PersonenRepository;
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
        if(person == null)
            throw new PersonenServiceException("Person darf nicht null sein");
        if(person.getVorname() == null || person.getVorname().length() < 2)
            throw new PersonenServiceException("Vorname zu kurz");
        throw new PersonenServiceException("Nachname zu kurz");
    }

    @Override
    public void aendern(Person person) throws PersonenServiceException {

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

    @Override
    public Optional<Person> findeNachId(String id) throws PersonenServiceException {
        return Optional.empty();
    }

    @Override
    public Iterable<Person> findeAlle() throws PersonenServiceException {
        return null;
    }

    // Select * from customers with ur
   /* @Transactional(isolation = Isolation.READ_UNCOMMITTED)
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

    */
}
