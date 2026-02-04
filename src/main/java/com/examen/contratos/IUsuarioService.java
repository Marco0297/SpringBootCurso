package com.examen.contratos;

import com.examen.entity.UsuarioEntity;
import com.examen.modelo.RespuestaAPIModel;
import com.examen.modelo.RespuestasAPISimpleModel;
import org.springframework.http.ResponseEntity;

public interface IUsuarioService {
    public ResponseEntity<RespuestasAPISimpleModel> crearUsuarios (int cantidad, String genero);

    public ResponseEntity<RespuestaAPIModel> getAllUsuarios();

    public ResponseEntity<RespuestaAPIModel> updUsuarioByNombre(String nombre);

    public ResponseEntity<RespuestaAPIModel> delUsuarioById(Long id);
}
