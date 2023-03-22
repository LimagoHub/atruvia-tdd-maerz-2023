package de.limago.webapp.presentation.controller;

import de.limago.webapp.presentation.dto.PersonDTO;
import de.limago.webapp.repository.PersonenRepository;
import de.limago.webapp.service.PersonenService;
import de.limago.webapp.service.PersonenServiceException;
import de.limago.webapp.service.model.Person;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
//@Sql({"/create.sql", "/insert.sql"})
class PersonenControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;// Nur lokale Pfade

    @MockBean // Spring Annotation
    private PersonenService serviceMock; // Macht einen UnitTest

//    @MockBean
//    private PersonenRepository repoMock;

    private final String validUUID = "b2e24e74-8686-43ea-baff-d9396b4202e0";
    private final PersonDTO validPersonDTO = PersonDTO.builder().id(validUUID).vorname("John").nachname("Doe").build();
    private final Person validPerson = Person.builder().id(validUUID).vorname("John").nachname("Doe").build();
    @Test
    void findById_test1() throws Exception{
        // Arrange
        when(serviceMock.findeNachId(anyString())).thenReturn(Optional.of(validPerson));

        //Action
        PersonDTO dto = restTemplate.getForObject("/v1/personen/b2e24e74-8686-43ea-baff-d9396b4202e0", PersonDTO.class);

        // Assertion
        assertEquals("John", dto.getVorname());
        assertEquals("Doe", dto.getNachname());
    }

    @Test
    void findById_test2() throws Exception{
        when(serviceMock.findeNachId(anyString())).thenReturn(Optional.of(validPerson));
        String wasAuchImmer = restTemplate.getForObject("/v1/personen/b2e24e74-8686-43ea-baff-d9396b4202e0", String.class);
        System.out.println(wasAuchImmer);
    }
    @Test
    void findById_test3() throws Exception{
        when(serviceMock.findeNachId(anyString())).thenReturn(Optional.of(validPerson));
        ResponseEntity<PersonDTO> entity = restTemplate.getForEntity("/v1/personen/b2e24e74-8686-43ea-baff-d9396b4202e0", PersonDTO.class);
        PersonDTO dto = entity.getBody();
        assertEquals("John", dto.getVorname());
        assertEquals("Doe", dto.getNachname());
        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @Test
    void findById_test4() throws Exception{
        when(serviceMock.findeNachId(anyString())).thenReturn(Optional.empty());
        ResponseEntity<PersonDTO> entity = restTemplate.getForEntity("/v1/personen/b2e24e74-8686-43ea-baff-d9396b4202e0", PersonDTO.class);

        assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
    }

    @Test
    void findAllo_test4() throws Exception{
        Iterable<Person> personen = List.of(Person.builder().id("1").vorname("John").nachname("Doe").build(),Person.builder().id("2").vorname("John").nachname("Rambo").build());

        when(serviceMock.findeAlle()).thenReturn(personen);
        ResponseEntity<List<PersonDTO>> entity = restTemplate.exchange("/v1/personen", HttpMethod.GET, null,new ParameterizedTypeReference<List<PersonDTO>>() { });

        assertEquals(HttpStatus.OK, entity.getStatusCode());
        List<PersonDTO> liste = entity.getBody();
//        assertThat(liste)
//                .isNotEmpty()
//                .contains("1")
//                .doesNotContainNull()
//                .containsSequence("2", "3");
        assertEquals(2,liste.size());
        for(var element : liste) {
            assertEquals("John",element.getVorname());
        }
    }

    @Test
    void save_HappyDay() throws Exception{

        // Arrange (Paket zum Versenden schnueren)
        PersonDTO dtoToSend =PersonDTO.builder().id(validUUID).vorname("John").nachname("Doe").build();

        HttpEntity<PersonDTO> httpEntity = new HttpEntity<>(dtoToSend);

        // Act (Restcall mit Post-Method)
        ResponseEntity<Void> entity = restTemplate.exchange("/v1/personen", HttpMethod.POST, httpEntity,Void.class);

        // Assert (Prüfen ob der richtige StatusCode, LocationHeader etc. zurück kommen)
        assertEquals(HttpStatus.CREATED, entity.getStatusCode());
        assertThat(entity.getHeaders().getLocation().toString(), Matchers.endsWith("/v1/personen/b2e24e74-8686-43ea-baff-d9396b4202e0"));

        // Pruefen ob der richtige Wert an den Service uebergeben wird
        verify(serviceMock).speichern(validPerson);
    }

    @Test
    void save_Antipath() throws Exception{

        // Arrange
        doThrow(new PersonenServiceException("Unerwuenschte Person")).when(serviceMock).speichern(any());

        PersonDTO dtoToSend =PersonDTO.builder().id(validUUID).vorname("John").nachname("Doe").build();

        HttpEntity<PersonDTO> httpEntity = new HttpEntity<>(dtoToSend);

        // Act
        ResponseEntity<Map<String, Object>> entity = restTemplate.exchange("/v1/personen", HttpMethod.POST, httpEntity,new ParameterizedTypeReference<Map<String, Object>>() { });


        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, entity.getStatusCode());
        var result = entity.getBody();
        assertEquals("Unerwuenschte Person", result.get("message"));

    }
    @Test
    void save_InternerServerError() throws Exception{


        doThrow(new PersonenServiceException("Interner Fehler")).when(serviceMock).speichern(any());

        PersonDTO dtoToSend =PersonDTO.builder().id(validUUID).vorname("John").nachname("Doe").build();

        HttpEntity<PersonDTO> httpEntity = new HttpEntity<>(dtoToSend);

        ResponseEntity<Map<String, Object>> entity = restTemplate.exchange("/v1/personen", HttpMethod.POST, httpEntity,new ParameterizedTypeReference<Map<String, Object>>() { });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, entity.getStatusCode());
        var result = entity.getBody();
        assertEquals("Interner Fehler", result.get("message"));

    }

    @ParameterizedTest
    @CsvSource({
            "true,         200",
            "false,        404"
    })
    void delete_als_parameter(boolean serviceresult, int expectedStatusCode) throws Exception{
        when(serviceMock.loesche(anyString())).thenReturn(serviceresult);
        ResponseEntity<Void> entity = restTemplate.exchange("/v1/personen/1", HttpMethod.DELETE, null, Void.class);
        assertEquals(expectedStatusCode,entity.getStatusCodeValue());
        verify(serviceMock).loesche("1");
    }

}