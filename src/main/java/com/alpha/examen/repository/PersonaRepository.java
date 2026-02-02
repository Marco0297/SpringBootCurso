package com.alpha.examen.repository;

import com.alpha.examen.model.PersonaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<PersonaModel,Long> {
}
