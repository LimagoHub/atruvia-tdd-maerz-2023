package de.limago.webapp.presentation.controller;


import de.limago.webapp.presentation.dto.PersonDTO;
import de.limago.webapp.presentation.mapper.PersonDtoMapper;
import de.limago.webapp.service.PersonenService;
import de.limago.webapp.service.PersonenServiceException;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
