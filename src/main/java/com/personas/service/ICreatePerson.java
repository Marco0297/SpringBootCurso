package com.personas.service;

import com.personas.model.PersonasModel;
import org.springframework.http.ResponseEntity;

/**
 * Contrato para crea un registro de tipo persona
 */
public interface ICreatePerson {
    public ResponseEntity<PersonasModel> createPerson(PersonasModel person);
}
