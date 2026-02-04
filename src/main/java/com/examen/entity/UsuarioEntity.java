package com.examen.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Entity
@Table(name = "Usuario")
@Setter
@Getter
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "Apellido")
    private String apellido;
    @Column(name = "Correo")
    private String correo;
    @Column(name = "TelCasa")
    private String telefonoCasa;
    @Column(name = "TelCelular")
    private String telefonoCelular;

}
