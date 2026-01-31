package com.personas.service;

import com.personas.model.PersonasModel;
import org.springframework.http.ResponseEntity;

/**
 * Contrato que actualiza un registro de tipo Peronas
 */
public interface IUpdatePerson {

    public ResponseEntity<PersonasModel> updateRegiter(Long id, PersonasModel personasModel);

}
