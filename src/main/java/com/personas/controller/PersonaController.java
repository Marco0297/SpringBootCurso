package com.personas.controller;

import com.personas.model.PersonasModel;
import com.personas.service.ICreatePerson;
import com.personas.service.IGetPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//"localhost:8080/api/v1"
@RestController
@RequestMapping(value = "/api/v1")
public class PersonaController {

    @Autowired
    private ICreatePerson iCreatePerson;

    @Autowired
    private IGetPerson iGetPerson;

    /**
     * localhost:8080/api/v1/add
     */
    @PostMapping(value = "/add")
    public ResponseEntity<PersonasModel>addPerson(@RequestBody PersonasModel personasModel){
        return iCreatePerson.createPerson(personasModel);
    }

    /**
     * localhost:8080/api/v1/get/all
     * @return
     */
    @GetMapping(value = "/get/all")
    public List<PersonasModel> getAllPersonas(){
        return iGetPerson.getAllPersonas();
    }

    /**
     * localhost:8080/api/v1/get/id
     * @param id
     * @return
     */
    @GetMapping(value = "/get/id")
    public ResponseEntity<PersonasModel> getIdPersonas(@RequestParam Long id){
        return iGetPerson.getPersonaById(id);
    }
}
