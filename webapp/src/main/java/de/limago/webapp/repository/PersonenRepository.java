package de.limago.webapp.repository;

import de.limago.webapp.repository.entities.PersonEntity;
import de.limago.webapp.repository.entities.TinyPerson;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PersonenRepository extends CrudRepository<PersonEntity, String> {
    Iterable<PersonEntity> findByVorname(String vorname);

    @Query("select p from PersonEntity p where p.vorname like :vorname")
    Iterable<PersonEntity> xxx(String vorname);

    @Query("select p.id, p.nachname from PersonEntity p")
    Iterable<Object[]> yyy();

    @Query("select new de.bankenit.webapp.repository.entities.TinyPerson(p.id, p.nachname) from PersonEntity p")
    Iterable<TinyPerson> zzz();
}
