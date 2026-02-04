package com.personas.repository;

import com.personas.model.EstudianteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<EstudianteModel ,Long > {
    List<EstudianteModel> findByFirst(String first);
}
