package com.personas.service.impl;

import com.personas.model.PersonasModel;
import com.personas.repository.PersonaRepository;
import com.personas.service.IGetPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Clase encargada de buscar registros Personas
 */
@Service
public class GetPersonServiceImp implements IGetPerson {

    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public List<PersonasModel> getAllPersonas() {
        return personaRepository.findAll();
    }

    @Override
    public ResponseEntity<PersonasModel> getPersonaById(Long id) {
        return personaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

        /**
         * Optipnal<PersonasModel> personaOpt =  personaRepository.findById(id);
         * if(personaOpt.isPresent()){
         *     return ResponseEntity.ok(personaOpt.get());
         * }else{
         *     return ResponseEntity.not_found().build();
         * }
         */
    }
}
