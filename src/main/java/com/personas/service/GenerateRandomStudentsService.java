package com.personas.service;

import com.personas.model.EstudianteModel;
import com.personas.response.ResponseApiRecord;
import com.personas.response.ResponseApiRecordDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenerateRandomStudentsService {

    @Autowired
    GetRandomStudentService getRandomStudentService;

    @Autowired
    ICreateStudents iCreateStudents;

    public ResponseEntity<ResponseApiRecord>  generateRandomStudents(int numStudents){
        ResponseEntity<ResponseApiRecordDetails> students = getRandomStudentService.getRandomStudent(numStudents);

        ResponseApiRecordDetails recordEstudents = students.getBody();

        assert recordEstudents != null; //No permite NullPointerException
        if(recordEstudents.msg().equals("500")){
            return ResponseEntity.internalServerError().body(new ResponseApiRecord( "No se logro hacer registro por falla de API-users"));
        }

        List<EstudianteModel> listStudents = recordEstudents.details();

        List<EstudianteModel> students1 = iCreateStudents.createStudents(listStudents);

        return ResponseEntity.ok(new ResponseApiRecord( "Se crearon registros en BD correctamente: Numero de registro " + numStudents));
    }
}
