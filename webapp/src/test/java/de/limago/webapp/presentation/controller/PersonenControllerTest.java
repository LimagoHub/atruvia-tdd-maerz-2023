package de.limago.webapp.presentation.controller;

import de.limago.webapp.presentation.dto.PersonDTO;
import de.limago.webapp.service.PersonenService;
import de.limago.webapp.service.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@Sql({"/create.sql", "/insert.sql"})
class PersonenControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private PersonenService serviceMock;

    private final String validUUID = "b2e24e74-8686-43ea-baff-d9396b4202e0";
    private final PersonDTO validPersonDTO = PersonDTO.builder().id(validUUID).vorname("John").nachname("Doe").build();
    private final Person validPerson = Person.builder().id(validUUID).vorname("John").nachname("Doe").build();
    @Test
    void findById_test1() throws Exception{
        when(serviceMock.findeNachId(anyString())).thenReturn(Optional.of(validPerson));
        PersonDTO dto = restTemplate.getForObject("/v1/personen/b2e24e74-8686-43ea-baff-d9396b4202e0", PersonDTO.class);
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

}