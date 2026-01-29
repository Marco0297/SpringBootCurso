package com.personas.model;

import com.personas.enums.Sexo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Personas_Table")
@Setter
@Getter
public class PersonasModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PERSON")
    private Long id;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "APELLIDO")
    private String apellido;

    @Column(name = "CURP")
    private String curp;

    @Enumerated(EnumType.STRING)
    @Column(name = "SEXO")
    private Sexo tipoSex;

    @Column(name = "EDAD")
    private int edad;
}
