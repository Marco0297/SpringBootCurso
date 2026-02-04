package com.personas.controller;

import com.personas.model.EstudianteModel;
import com.personas.response.ResponseApiRecord;
import com.personas.response.ResponseApiRecordDetails;
import com.personas.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/student")
public class StudentController {
    @Autowired
    GetRandomStudentService getRandomStudentService;
    @Autowired
    GenerateRandomStudentsService generateRandomStudentsService;
    @Autowired
    IAllStudents iAllStudents;
    @Autowired
    IUpdateStudent iUpdateStudent;
    @Autowired
    IDeleteStudent iDeleteStudent;

    /*@GetMapping(value = "/get/{numberStudents}")
    public ResponseEntity<ResponseApiRecord> getEstudents(@PathVariable int numberStudents) {
        return getRandomStudentService.getRandomStudent(numberStudents);
    }*/

    /***
     * localhost:8080/student/registro?registro=n
     * @param registro Es elnumero de estudiantes que vamos a generar
     * @return
     */
    @PutMapping(value = "/registro")
    public ResponseEntity<ResponseApiRecord>generateStudents(@RequestParam int registro) {
        return generateRandomStudentsService.generateRandomStudents(registro);
    }

    /***
     * localhost:8080/student/allRegisters
     * @return
     */

    @GetMapping(value = "allRegisters")
    public ResponseEntity<ResponseApiRecordDetails> getAllEstudiantes() {
        return iAllStudents.getAllStudents();
    }

    /***
     * localhost:8080/student/update/[{NOMBRE}]
     * @param nombre
     * @param estudianteModel
     * @return
     */
    @PutMapping(value = "/update/{nombre}")
    public ResponseEntity<ResponseApiRecordDetails> updateStudent(@PathVariable String nombre, @RequestBody EstudianteModel estudianteModel) {
        return iUpdateStudent.updateStudentByName(nombre, estudianteModel);
    }

    /***
     * localhost:8080/student/delete/{id}
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseApiRecordDetails> deleteStudent(@PathVariable Long id) {
        return iDeleteStudent.deleteStudent(id);
    }
}
