package de.limago.webapp.repository;

import de.limago.webapp.repository.entities.PersonEntity;
import de.limago.webapp.repository.entities.TinyPerson;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PersonenRepository extends CrudRepository<PersonEntity, String> {

}
