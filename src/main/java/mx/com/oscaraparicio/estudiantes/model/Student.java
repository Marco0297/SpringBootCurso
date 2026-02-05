package mx.com.oscaraparicio.estudiantes.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Student (Entity)
 * ================
 * Entity = clase que se mapea a una tabla en BD.
 */
@Getter
@Setter
@Entity
@Table(name = "students")
public class Student {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private Long id;


    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String email;
    private String phone;
    private String cell;


    public Student() {
      //  constructor vacio obligatorio para JPA
    }

    /**
     * Constructor con params
     */
    public Student(String firstName, String lastName, String email, String phone, String cell) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.cell = cell;
    }
}
