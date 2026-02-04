package com.personas.service.impl;

import com.personas.model.EstudianteModel;
import com.personas.repository.StudentRepository;
import com.personas.response.ResponseApiRecordDetails;
import com.personas.service.IDeleteStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IDeleteStudentsImpl implements IDeleteStudent {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public ResponseEntity<ResponseApiRecordDetails> deleteStudent(Long id) {

        Optional<EstudianteModel> studentDelete = studentRepository.findById(id);
        boolean exists = studentDelete.isPresent();
        studentDelete.ifPresent(student -> {
            studentRepository.delete(student);
        });

        if (!exists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseApiRecordDetails("No se encontraron registros en BD",null));
        }
        return ResponseEntity.ok().body(new ResponseApiRecordDetails("Registro con ID {"+id+"}, eliminado correctamente",studentRepository.findAll()));
    }

}
