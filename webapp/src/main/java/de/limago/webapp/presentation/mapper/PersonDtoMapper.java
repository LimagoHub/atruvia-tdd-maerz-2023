package de.limago.webapp.presentation.mapper;


import de.limago.webapp.presentation.dto.PersonDTO;
import de.limago.webapp.service.model.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonDtoMapper {
    PersonDTO convert(Person person);
    Person convert(PersonDTO dto);
    Iterable<PersonDTO> convert(Iterable<Person> personen);
}
