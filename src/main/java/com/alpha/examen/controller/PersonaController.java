package com.alpha.examen.controller;

import com.alpha.examen.response.ResponseApiRecord;
import com.alpha.examen.service.IPersona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonaController {

    @Autowired
    private IPersona iPersona;


    @GetMapping(value = "/student/{registro}")
    public ResponseEntity<ResponseApiRecord> createPerson(@PathVariable int registro) {
        return iPersona.createPerson(registro);
    }
}
