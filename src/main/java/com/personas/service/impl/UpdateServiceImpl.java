package com.personas.service.impl;

import com.personas.model.PersonasModel;
import com.personas.repository.PersonaRepository;
import com.personas.service.IUpdatePerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateServiceImpl implements IUpdatePerson {

    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public ResponseEntity<PersonasModel> updateRegiter(Long id, PersonasModel personasModel) {
        Optional<PersonasModel> personasModelOptional = personaRepository.findById(id);
        if (personasModelOptional.isPresent()) {
            PersonasModel personasModel1 = personasModelOptional.get();
            personasModel1.setNombre(personasModel.getNombre());
            personasModel1.setApellido(personasModel.getApellido());
            personasModel1.setCurp(personasModel.getCurp());
            personasModel1.setTipoSex(personasModel.getTipoSex());
            personasModel1.setEdad(personasModel.getEdad());

            PersonasModel updatePerson = personaRepository.saveAndFlush(personasModel1);
            return ResponseEntity.ok(updatePerson);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
