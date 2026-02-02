package com.alpha.examen.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
@Table(name = "Persona" )

public class PersonaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PERSON")
    private Long id;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "APELLIDO")
    private String apellido;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "CELL")
    private String cell;

    @Column(name = "GENERO")
    private String genero;

    protected PersonaModel() {
    }

    public PersonaModel(String nombre, String apellido, String email, String phone, String cell, String genero) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.phone = phone;
        this.cell = cell;
        this.genero = genero;
    }
}
