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
}
