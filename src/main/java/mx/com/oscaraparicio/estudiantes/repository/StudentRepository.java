package mx.com.oscaraparicio.estudiantes.repository;

import mx.com.oscaraparicio.estudiantes.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByFirstNameIgnoreCase(String firstName);


}
