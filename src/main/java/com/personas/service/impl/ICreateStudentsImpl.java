package com.personas.service.impl;

import com.personas.model.EstudianteModel;
import com.personas.repository.StudentRepository;
import com.personas.service.ICreateStudents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ICreateStudentsImpl implements ICreateStudents {
    @Autowired
    StudentRepository  studentRepository;

    @Override
    public List<EstudianteModel> createStudents(List<EstudianteModel> estudiantes){
        return studentRepository.saveAll(estudiantes);
    }
}
