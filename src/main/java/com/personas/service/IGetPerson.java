package com.personas.service;

import com.personas.model.PersonasModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Contrato que obtiene los registros
 */
public interface IGetPerson {
    List<PersonasModel>getAllPersonas();
    ResponseEntity<PersonasModel> getPersonaById(Long id);
    List<PersonasModel> getByEdadRange(int edadMin, int edadMax);
}
