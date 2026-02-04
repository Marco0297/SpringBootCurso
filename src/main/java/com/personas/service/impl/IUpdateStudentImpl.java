package com.personas.service.impl;

import com.personas.model.EstudianteModel;
import com.personas.repository.StudentRepository;
import com.personas.response.ResponseApiRecordDetails;
import com.personas.service.IUpdateStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IUpdateStudentImpl implements IUpdateStudent {
    @Autowired
    StudentRepository studentRepository;

    @Override
    public ResponseEntity<ResponseApiRecordDetails>  updateStudentByName(String name, EstudianteModel estudianteModel){

       List<EstudianteModel> studentList =  studentRepository.findByFirst(name);

        int size = studentList.size();
        if(size>1){
           return  ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseApiRecordDetails("Existen estudiantes con el mimo nombre : " + size , studentList));
        } else if (studentList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseApiRecordDetails("No existen registros con nombre {" + name + "}", null));
        }else{//encontro solo un estudiante

            EstudianteModel studentUpdate = new EstudianteModel();
            studentUpdate.setId(studentList.get(0).getId());
            studentUpdate.setFirst(estudianteModel.getFirst());
            studentUpdate.setLast(estudianteModel.getLast());
            studentUpdate.setEmail(estudianteModel.getEmail());
            studentUpdate.setPhone(estudianteModel.getPhone());
            studentUpdate.setCell(estudianteModel.getCell());

            EstudianteModel studentUpdated = studentRepository.save(studentUpdate);
            List<EstudianteModel> listStudentUpdated = new ArrayList<>();
            listStudentUpdated.add(studentUpdated);

            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseApiRecordDetails("Registro actualizado correctamente", listStudentUpdated));
        }
    }
}
