package com.personas.controller;

import com.personas.model.PersonasModel;
import com.personas.service.ICreatePerson;
import com.personas.service.IDeletePerson;
import com.personas.service.IGetPerson;
import com.personas.service.IUpdatePerson;
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

    @Autowired
    private IUpdatePerson iUpdatePerson;

    @Autowired
    private IDeletePerson ideletePerson;

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
     * localhost:8080/api/v1/get/id?id
     * @param id
     * @return
     */
    @GetMapping(value = "/get/id")
    public ResponseEntity<PersonasModel> getIdPersonas(@RequestParam Long id){
        return iGetPerson.getPersonaById(id);
    }

    /**
     * localhost:8080/api/v1/update/id?id
     * @param id
     * @return
     */
    @PutMapping(value = "/update/id")
    public ResponseEntity<PersonasModel> updatePerson(@RequestParam Long id, @RequestBody PersonasModel personasModel){
        return  iUpdatePerson.updateRegiter(id, personasModel);
    }

    /**
     * localhost:8080/api/v1/delete/id?id
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete(id")
    public ResponseEntity<Void> deleteById(@RequestParam Long id){
        return ideletePerson.deletePersonById(id);
    }

    /**
     * localhost:8080/api/v1/get/byEdad?edadMin=''&edadMax=''
     * @param edadMin
     * @param edadMax
     * @return
     */
    @GetMapping(value = "/get/byEdad")
    public List<PersonasModel> getByAge(@RequestParam int edadMin, @RequestParam int edadMax){
        return iGetPerson.getByEdadRange(edadMin, edadMax);
    }
}
