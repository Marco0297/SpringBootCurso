package mx.com.oscaraparicio.estudiantes.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentUpdateRequest {
    private String lastName;
    private String email;
    private String phone;
    private String cell;

    public StudentUpdateRequest() {
        // para crear el objeto y luego ir llenando campos con setters
    }
}
