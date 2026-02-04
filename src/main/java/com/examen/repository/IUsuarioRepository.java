package com.examen.repository;

import com.examen.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUsuarioRepository extends JpaRepository<UsuarioEntity,Long>{
    List<UsuarioEntity> findByNombre(String nombre);
}
