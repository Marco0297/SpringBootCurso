package com.examen.controller;

import com.examen.contratos.IUsuario;
import com.examen.entity.UsuarioEntity;
import com.examen.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/view")
public class UsuarioController implements IUsuario {

    @Autowired
    IUsuarioRepository iUsuarioRepository;

    //http://localhost:8080/view/usuarios
    @GetMapping("/usuarios")
    public String index(Model model){
        model.addAttribute("titulo","Tabla de usuarios");
        model.addAttribute("usuarios",getUsuarios());
        return "usuario/index";//Nombre de la vista en resourse que carga
    }

    @GetMapping("/usuarios/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", getUsuarioById(id));
        return "usuario/editar";
    }

    //Thymeleaf + HTML → POST / GET
    //REST API → PUT / DELETE
    @PostMapping("/usuarios/editar")
    public String guardarEdicion(@ModelAttribute UsuarioEntity usuario) {
        iUsuarioRepository.save(usuario);
        return "redirect:/view/usuarios";
    }

    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        iUsuarioRepository.deleteById(id);
        return "redirect:/view/usuarios";
    }

    private Optional<UsuarioEntity> getUsuarioById(Long id){
        Optional<UsuarioEntity> usuario;
        return usuario = iUsuarioRepository.findById(id);
    }

    private List<UsuarioEntity> getUsuarios(){
        List<UsuarioEntity> usuarios;
        return usuarios = iUsuarioRepository.findAll();
    }
}
