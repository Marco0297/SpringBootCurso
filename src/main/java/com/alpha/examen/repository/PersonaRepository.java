package com.alpha.examen.repository;

import com.alpha.examen.model.PersonaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonaRepository extends JpaRepository<PersonaModel,Long> {
    List<PersonaModel> findByNombre(String nombre);
}
