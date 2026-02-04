package com.examen.service;

import com.examen.contratos.IUsuarioService;
import com.examen.entity.UsuarioEntity;
import com.examen.modelo.RespuestaAPIModel;
import com.examen.modelo.RespuestasAPISimpleModel;
import com.examen.repository.IUsuarioRepository;
import com.examen.service.APIExterna.RandomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UsuarioService implements IUsuarioService {
    RespuestasAPISimpleModel bodySimple = new RespuestasAPISimpleModel();
    RespuestaAPIModel body = new RespuestaAPIModel();

    @Autowired
    RandomUserService randomUserService;

    @Autowired
    IUsuarioRepository iUsuarioRepository;

    @Override
    public ResponseEntity<RespuestasAPISimpleModel> crearUsuarios(int cantidad, String genero) {
        int cont = 0;

        if(cantidad > 0){
            for(int i = 0; i < cantidad; i++)
            {
                UsuarioEntity usuario = randomUserService.getUserFromAPIByGender(genero);
                if (usuario.getId() != 0)
                {
                    usuario.setId(null);//Limpio el Id para que sea un nuevo registro
                    iUsuarioRepository.save(usuario);
                    cont += 1;
                }else{
                    break; //Si por algo no responde la api sale del ciclo y termina
                }
            }
        }else {
            bodySimple.msg = "Se tiene que ingresar un numero mayor a 0";
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(bodySimple);
        }

        if(cont == cantidad){
            bodySimple.msg = "Se crearon registros en BD correctamente: Numero de registros: "+cont;
            return ResponseEntity.status(HttpStatus.CREATED).body(bodySimple);
        }else {
            bodySimple.msg = "No se logro hacer registro por falla de API-users, Solo se insertaron: "+cont;
            return ResponseEntity.status(HttpStatus.CONFLICT).body(bodySimple);
        }

    }

    @Override
    public ResponseEntity<RespuestaAPIModel> getAllUsuarios() {
        List<UsuarioEntity> usuarios = iUsuarioRepository.findAll();

        if(usuarios.size() > 0){
            body.msg = "Registros encontrados : "+usuarios.size();
            body.details = usuarios;
            return  ResponseEntity.status(HttpStatus.OK).body(body);
        }else {
            body.msg = "No se encontraron registros en BD";
            body.details = null;
            return  ResponseEntity.status(HttpStatus.FOUND).body(body);
        }

    }

    @Override
    public ResponseEntity<RespuestaAPIModel> updUsuarioByNombre(String nombre) {
        ResponseEntity respuesta;
        List<UsuarioEntity> usuarios = iUsuarioRepository.findByNombre(nombre);

        switch (usuarios.size()){
            case 0: //Sin registros
                body.msg = "No existen registros con nombre {"+nombre+"}";
                body.details = null;
                respuesta =  ResponseEntity.status(HttpStatus.FOUND).body(body);
                break;
            case 1: //Hay uno
                usuarios.get(0).setNombre("ACTUALIZADO");//Actualizar el nombre de la lista
                UsuarioEntity usuario = usuarios.get(0);//Guardarlo en un modelo unico
                iUsuarioRepository.save(usuario);//Actualiar el registro
                body.msg = "Registro actualizado correctamente";
                body.details = usuarios;
                respuesta =  ResponseEntity.status(HttpStatus.OK).body(body);
                break;
            default: //Hay mas de 1 registro
                body.msg = "Existen estudiantes con el mimo nombre, n: "+usuarios.size();
                body.details = usuarios;
                respuesta =  ResponseEntity.status(HttpStatus.CONFLICT).body(body);
                break;
        }

        return respuesta;
    }

    @Override
    public ResponseEntity<RespuestaAPIModel> delUsuarioById(Long id) {
        if(iUsuarioRepository.existsById(id)){
            iUsuarioRepository.deleteById(id);
            body.msg = "Registro con ID {"+id+"}, eliminado correctamente";
            body.details = iUsuarioRepository.findAll();//Regresar todos los que quedan
            return  ResponseEntity.status(HttpStatus.OK).body(body);
        }else {
            body.msg = "No se encontraron registros en BD";
            body.details = null;
            return  ResponseEntity.status(HttpStatus.FOUND).body(body);
        }

    }
}
