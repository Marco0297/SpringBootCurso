package com.personas.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Contrato para eliminar cualquier registro de tipo Persona
 */
public interface IDeletePerson {

    public ResponseEntity<Void> deletePersonById(Long id);
}
