package de.limago.webapp.service.internal;

import de.limago.webapp.repository.PersonenRepository;
import de.limago.webapp.service.PersonenServiceException;
import de.limago.webapp.service.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PersonenServiceImplTest {

    @InjectMocks
    private PersonenServiceImpl objectUnderTest;

    @Mock
    private PersonenRepository repoMock;

    @Test
    void speichern_parameterIsNull_throwsPersonenServiceException() throws Exception{
        Person invalid = null;
        PersonenServiceException ex = assertThrows(PersonenServiceException.class,()->objectUnderTest.speichern(invalid));
        assertEquals("Person darf nicht null sein", ex.getMessage());
    }
    @Test
    void speichern_vornameNull_throwsPersonenServiceException() throws Exception{
        Person invalid = Person.builder().id("1").vorname(null).nachname("Doe").build();
        PersonenServiceException ex = assertThrows(PersonenServiceException.class,()->objectUnderTest.speichern(invalid));
        assertEquals("Vorname zu kurz", ex.getMessage());
    }
    @Test
    void speichern_vornameTooShort_throwsPersonenServiceException() throws Exception{
        Person invalid = Person.builder().id("1").vorname("J").nachname("Doe").build();
        PersonenServiceException ex = assertThrows(PersonenServiceException.class,()->objectUnderTest.speichern(invalid));
        assertEquals("Vorname zu kurz", ex.getMessage());
    }
    @Test
    void speichern_nachnameNull_throwsPersonenServiceException() throws Exception{
        Person invalid = Person.builder().id("1").vorname("John").nachname(null).build();
        PersonenServiceException ex = assertThrows(PersonenServiceException.class,()->objectUnderTest.speichern(invalid));
        assertEquals("Nachname zu kurz", ex.getMessage());
    }
}