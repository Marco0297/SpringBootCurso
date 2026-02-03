package com.alpha.examen.controller;

import com.alpha.examen.model.PersonaModel;
import com.alpha.examen.response.ResponseApiRecord;
import com.alpha.examen.service.IPersona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonaController {

    @Autowired
    private IPersona iPersona;


    @PostMapping(value = "/student")
    public ResponseEntity<ResponseApiRecord> creaPerson(@RequestParam int registro) {
        return iPersona.createPerson(registro);
    }

    @GetMapping(value = "/student/allRegisters")
    public ResponseEntity<ResponseApiRecord> consiguePerson() {
        return iPersona.getPersonsAll();
    }

    @PutMapping(value = "/student/update/{nombre}")
    public ResponseEntity<ResponseApiRecord> actualizaPersonPorNombre(@PathVariable String nombre, @RequestBody PersonaModel personaModel) {
        return iPersona.updatePersonByName(nombre,personaModel);
    }

    @DeleteMapping(value = "/student/delete/{id}")
    public ResponseEntity<ResponseApiRecord> eliminaPersonPorId(@PathVariable Long id) {
        return iPersona.deletePersonById(id);
    }
}
