package com.personas.service.impl;

import com.personas.model.PersonasModel;
import com.personas.repository.PersonaRepository;
import com.personas.service.ICreatePerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Clase servicio que contendra la logica para crear un registro de tipo PersonaModel
 */
@Service
public class CreatePersonImpl implements ICreatePerson {

    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public ResponseEntity<PersonasModel> createPerson(PersonasModel personasModel) {
            PersonasModel crear = personaRepository.save(personasModel);
        //return ResponseEntity.ok().body(crear); // retorna un 200 -> body
        //return new ResponseEntity<>(crear, HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(crear);
    }
}
