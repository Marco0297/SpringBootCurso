package com.personas.service.impl;

import com.personas.model.EstudianteModel;
import com.personas.repository.StudentRepository;
import com.personas.response.ResponseApiRecordDetails;
import com.personas.service.IAllStudents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IAllStudentsImpl implements IAllStudents {
    @Autowired
    StudentRepository studentRepository;
    @Override
    public ResponseEntity<ResponseApiRecordDetails> getAllStudents() {

        List<EstudianteModel> allStudents = studentRepository.findAll();
        if (allStudents.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseApiRecordDetails("No se encontraron registros en BD" , null)) ;
        }
        return ResponseEntity.ok(new ResponseApiRecordDetails("Registros encontrados " + allStudents.size() , allStudents)) ;
    }
}
