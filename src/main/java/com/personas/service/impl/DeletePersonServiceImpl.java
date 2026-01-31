package com.personas.service.impl;

import com.personas.repository.PersonaRepository;
import com.personas.service.IDeletePerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DeletePersonServiceImpl implements IDeletePerson {

    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public ResponseEntity<Void> deletePersonById(Long id) {
        if (personaRepository.existsById(id)) {
            personaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
            //return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
