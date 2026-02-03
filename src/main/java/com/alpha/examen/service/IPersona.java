package com.alpha.examen.service;

import com.alpha.examen.model.PersonaModel;
import com.alpha.examen.response.ResponseApiRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface IPersona {
    ResponseEntity<ResponseApiRecord> createPerson(int cantidad);

    ResponseEntity<ResponseApiRecord> getPersonsAll();

    ResponseEntity<ResponseApiRecord> updatePersonByName(String nombre, PersonaModel personaModel);

    ResponseEntity<ResponseApiRecord> deletePersonById(Long id);
}
