package com.sistemagestionestudiantes.model;
import jakarta.persistence.*;
import lombok.Data;

// Se agrega el @Data para hacer uso de los setters y getters implicitos para guardar la información
@Data
@Entity
@Table(name = "Students")
public class StudentsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_STUDENT")
    private Long id;

    @Column(name = "FIRSTNAME")
    private String firstName;
    @Column(name = "LASTNAME")
    private String lastName;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "CELL")
    private String cell;

}
