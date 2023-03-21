package de.limago.webapp.service;

import de.limago.webapp.service.model.Person;

public interface BlacklistService {

    boolean isBlacklisted(Person person); // Potenziell ein RuntimeException
}
