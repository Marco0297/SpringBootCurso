package com.alpha.examen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExamenApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamenApplication.class, args);
	}


    //http://localhost:8080/student?registro=5 -- crear usuaurios

    //http://localhost:8080/student/allRegisters -- conseguir info d usuarios

    //http://localhost:8080/student/update/{nombre} -- actualizar por nombre
    /*
    {
    "nombre": "Mercier",
    "apellido": "Laurent",
    "email": "marilou.laurent@example.com",
    "phone": "05-16-34-30-02",
    "cell": "06-36-47-46-11",
    "genero": "male"
}
     */

    //http://localhost:8080/student/delete/{id} -- borrar por ID
}
