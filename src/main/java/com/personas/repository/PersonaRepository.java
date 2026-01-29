package com.personas.repository;

import com.personas.model.PersonasModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Clase que interactuca con base de datos mediante JPA
 */
@Repository
public interface PersonaRepository extends JpaRepository<PersonasModel,Long> {

}
