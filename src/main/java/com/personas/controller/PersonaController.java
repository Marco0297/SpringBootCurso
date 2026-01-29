package com.personas.controller;

import com.personas.model.PersonasModel;
import com.personas.service.ICreatePerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//"localhost:8080/api/v1"
@RestController
@RequestMapping(value = "/api/v1")
public class PersonaController {

    @Autowired
    private ICreatePerson iCreatePerson;

    /**
     * localhost:8080/api/v1/add
     */
    @PostMapping(value = "/add")
    public ResponseEntity<PersonasModel>addPerson(@RequestBody PersonasModel personasModel){
        System.out.println("OBJETO RECIBIDO: "+personasModel);
        return iCreatePerson.createPerson(personasModel);
    }
}
