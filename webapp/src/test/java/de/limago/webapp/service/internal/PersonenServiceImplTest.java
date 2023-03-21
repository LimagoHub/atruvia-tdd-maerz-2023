package de.limago.webapp.service.internal;

import de.limago.webapp.repository.PersonenRepository;
import de.limago.webapp.repository.entities.PersonEntity;
import de.limago.webapp.service.BlacklistService;
import de.limago.webapp.service.PersonenServiceException;
import de.limago.webapp.service.mapper.PersonMapper;
import de.limago.webapp.service.model.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonenServiceImplTest {

    @InjectMocks
    private PersonenServiceImpl objectUnderTest;

    @Mock
    private PersonenRepository repoMock;

    @Mock
    private PersonMapper mapperMock;

    @Mock
    private BlacklistService blacklistMock;

    @Nested
    @DisplayName("Einfache Validierungstests")
    class simpleValidation {
        @Test
        void speichern_parameterIsNull_throwsPersonenServiceException() throws Exception {
            Person invalid = null;
            PersonenServiceException ex = assertThrows(PersonenServiceException.class, () -> objectUnderTest.speichern(invalid));
            assertEquals("Person darf nicht null sein", ex.getMessage());
        }

        @Test
        void speichern_vornameNull_throwsPersonenServiceException() throws Exception {
            Person invalid = Person.builder().id("1").vorname(null).nachname("Doe").build();
            PersonenServiceException ex = assertThrows(PersonenServiceException.class, () -> objectUnderTest.speichern(invalid));
            assertEquals("Vorname zu kurz", ex.getMessage());
        }

        @Test
        void speichern_vornameTooShort_throwsPersonenServiceException() throws Exception {
            Person invalid = Person.builder().id("1").vorname("J").nachname("Doe").build();
            PersonenServiceException ex = assertThrows(PersonenServiceException.class, () -> objectUnderTest.speichern(invalid));
            assertEquals("Vorname zu kurz", ex.getMessage());
        }

        @Test
        void speichern_nachnameNull_throwsPersonenServiceException() throws Exception {
            Person invalid = Person.builder().id("1").vorname("John").nachname(null).build();
            PersonenServiceException ex = assertThrows(PersonenServiceException.class, () -> objectUnderTest.speichern(invalid));
            assertEquals("Nachname zu kurz", ex.getMessage());
        }

        @Test
        void speichern_nachnametooShort_throwsPersonenServiceException() throws Exception {
            Person invalid = Person.builder().id("1").vorname("John").nachname("D").build();
            PersonenServiceException ex = assertThrows(PersonenServiceException.class, () -> objectUnderTest.speichern(invalid));
            assertEquals("Nachname zu kurz", ex.getMessage());
        }
    }


    @Test
    void speichern_antipath_throwsPersonenServiceException() throws Exception{
        when(blacklistMock.isBlacklisted(any())).thenReturn(true);
        Person invalid = Person.builder().id("1").vorname("John").nachname("Doe").build();
        PersonenServiceException ex = assertThrows(PersonenServiceException.class,()->objectUnderTest.speichern(invalid));
        assertEquals("Unerwuenschte Person", ex.getMessage());
    }

    @Test
    void speichern_unexpectedExceptionInUnderLyingService_throwsPersonenServiceException() throws Exception{

        // Wenn Methode nicht void
        //when().thenThrow
        when(blacklistMock.isBlacklisted(any())).thenReturn(false);
        doThrow(ArithmeticException.class).when(repoMock).save(any());

        Person valid = Person.builder().id("1").vorname("Joe").nachname("Doe").build();
        PersonenServiceException ex = assertThrows(PersonenServiceException.class,()->objectUnderTest.speichern(valid));
        assertEquals("Interner Fehler", ex.getMessage());
        assertEquals(ArithmeticException.class, ex.getCause().getClass());
    }

    @Test
    void speichern_validPerson_PersonPassedToRepo() throws Exception{
        // Arrange
        PersonEntity entity = PersonEntity.builder().id("4711").vorname("Fritz").nachname("Schmitt").build();
        Person validPerson = Person.builder().id("1").vorname("John").nachname("Doe").build();
        when(mapperMock.convert((Person) any())).thenReturn(entity);
        when(blacklistMock.isBlacklisted(any())).thenReturn(false);
        //Act
        objectUnderTest.speichern(validPerson);
        // Assertion

        InOrder inOrder = inOrder(blacklistMock, mapperMock,repoMock);
        inOrder.verify(blacklistMock).isBlacklisted(validPerson);
        inOrder.verify(mapperMock).convert(validPerson);
        inOrder.verify(repoMock).save(entity);

    }
}