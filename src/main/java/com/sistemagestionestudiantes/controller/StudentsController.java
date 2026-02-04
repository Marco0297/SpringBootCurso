package com.sistemagestionestudiantes.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.sistemagestionestudiantes.model.StudentsModel;
import com.sistemagestionestudiantes.response.ResponseApiDetail;
import com.sistemagestionestudiantes.response.ResponseApiRecord;
import com.sistemagestionestudiantes.service.RandomStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student") // Base path para los endpoints
public class StudentsController {

    @Autowired
    private RandomStudentService randomStudentService;

    @GetMapping("/registro")
    public ResponseEntity<ResponseApiRecord> crearEstudiantes(@RequestParam("registro") Integer n) throws JsonProcessingException {
        return randomStudentService.creaStudent(n);
    }

    // Endpoint para ver todos los registros de estudiantes creados
    @GetMapping("/allRegisters")
    public ResponseEntity<ResponseApiDetail> getAllStudents() {
        return randomStudentService.getAllStudents();
    }

    // Endpoint para actualizar un registro por nombre
    @PutMapping(value = "/update/{NOMBRE}")
    public ResponseEntity<ResponseApiDetail> updateStudent(@PathVariable("NOMBRE") String nombre, @RequestBody StudentsModel studentsModel){
        return  randomStudentService.updateStudent(nombre, studentsModel);
    }


    // Endpoint para ver todos los registros de estudiantes creados
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<ResponseApiDetail> deleteStudent(@PathVariable Long id) {
        return randomStudentService.deleteStudentById(id);
    }

}
