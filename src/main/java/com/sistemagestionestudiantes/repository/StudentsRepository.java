package com.sistemagestionestudiantes.repository;

import com.sistemagestionestudiantes.model.StudentsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Repository
 * Es la contextualización de la clase, esta es una clase hija del crud repository , para marcar una clase como un componente
 */
@Repository
public interface StudentsRepository extends JpaRepository<StudentsModel,Long> {

    /*
Se realizan filtros personalizados o consultas especializadas con el find by, en este caso que busque el firstname o todos los registros que con cuerden con el firstname
 */

    List<StudentsModel> findAllByFirstName(String firstName);

}

