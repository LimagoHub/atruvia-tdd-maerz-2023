package de.limago.webapp.service;

import de.limago.webapp.service.model.Person;

import java.util.Optional;

public interface PersonenService {

    void speichern(Person person) throws PersonenServiceException;
    void speichern(String vorname, String nachname) throws PersonenServiceException;
    void aendern(Person person) throws PersonenServiceException;
    boolean loesche(String id) throws PersonenServiceException;

    Optional<Person> findeNachId(String id) throws PersonenServiceException;

    Iterable<Person> findeAlle() throws PersonenServiceException;
}
