package com.examen.controller.service;

import com.examen.contratos.IUsuarioService;
import com.examen.entity.UsuarioEntity;
import com.examen.modelo.RespuestaAPIModel;
import com.examen.modelo.RespuestasAPISimpleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//"localhost:8080/student"
@RestController
@RequestMapping ("/student")
public class UsuarioServiceController {

    @Autowired
    private IUsuarioService iUsuarioService;

    //http://localhost:8080/student?cantidad=3&genero=female
    @PostMapping
    public ResponseEntity<RespuestasAPISimpleModel> addUsuario(@RequestParam int cantidad, String genero){
        return iUsuarioService.crearUsuarios(cantidad,genero);//Recibe y Ejecuta el servicio
    }

    //http://localhost:8080/student/allRegisters
    @GetMapping(value = "/allRegisters")
    public ResponseEntity<RespuestaAPIModel> getAllUsuarios(){
        return iUsuarioService.getAllUsuarios();
    }

    //http://localhost:8080/student/update?nombre=Sylvia
    @PutMapping(value = "/update")
    public ResponseEntity<RespuestaAPIModel> updUsuarioByNombre(@RequestParam String nombre) {
        return iUsuarioService.updUsuarioByNombre(nombre);
    }

    //http://localhost:8080/student/delete?id=2
    @DeleteMapping(value = "/delete")
    public ResponseEntity<RespuestaAPIModel> delUsuarioById(@RequestParam Long id) {
        return iUsuarioService.delUsuarioById(id);
    }
}
