package de.limago.webapp.presentation.controller;


import de.limago.webapp.presentation.dto.PersonDTO;
import de.limago.webapp.presentation.mapper.PersonDtoMapper;
import de.limago.webapp.service.PersonenService;
import de.limago.webapp.service.PersonenServiceException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/personen")
@RequiredArgsConstructor
public class PersonenController {

    private final PersonenService service;
    private final PersonDtoMapper mapper;

    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDTO> findPersonById(@PathVariable  String id) throws PersonenServiceException {
        return ResponseEntity.of(service.findeNachId(id).map(mapper::convert));
    }


    @GetMapping(path="", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<PersonDTO>> findAll(
            @RequestParam(required = false, defaultValue = "") String vorname,
            @RequestParam(required = false, defaultValue = "") String nachname
    ) throws PersonenServiceException {

        return ResponseEntity.ok(mapper.convert(service.findeAlle()));
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> save(@Valid @RequestBody  PersonDTO person, UriComponentsBuilder builder ) throws Exception{

        service.speichern(mapper.convert(person));
        var uri = builder.path("/v1/personen/{id}").buildAndExpand(person.getId());
        return ResponseEntity.created(uri.toUri()).build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> remove(@PathVariable String id ) throws Exception{

        if(service.loesche(id))
                return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

}
