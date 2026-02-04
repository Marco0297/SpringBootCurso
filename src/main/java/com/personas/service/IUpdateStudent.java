package com.personas.service;

import com.personas.model.EstudianteModel;
import com.personas.response.ResponseApiRecordDetails;
import org.springframework.http.ResponseEntity;

public interface IUpdateStudent {
    public ResponseEntity<ResponseApiRecordDetails> updateStudentByName(String name, EstudianteModel estudianteModel);
}
