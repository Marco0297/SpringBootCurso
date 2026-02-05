package controller;

import dto.ApiResponseDTO;
import entity.Student;
import service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService service;

    // 1. Registro
    @PostMapping("/registro")
    public ResponseEntity<ApiResponseDTO> register(@RequestParam int registro) {
        try {
            int created = service.registerStudents(registro);
            return ResponseEntity.ok(
                    new ApiResponseDTO(
                            "Se crearon registros en BD correctamente: Numero de registros",
                            created
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO(
                            "No se logro hacer registro por falla de API-users",
                            null
                    ));
        }
    }

    // 2. Obtener todos
    @GetMapping("/allRegisters")
    public ResponseEntity<ApiResponseDTO> getAll() {
        List<Student> students = service.findAll();

        if (students.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseDTO(
                            "No se encontraron registros en BD",
                            null
                    ));
        }

        return ResponseEntity.ok(
                new ApiResponseDTO(
                        "Registros encontrados",
                        students
                )
        );
    }

    // 3. Update por nombre
    @PutMapping("/update/{name}")
    public ResponseEntity<ApiResponseDTO> updateByName(@PathVariable String name) {
        List<Student> students = service.updateByName(name);

        if (students.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseDTO(
                            "No existen registros con nombre " + name,
                            null
                    ));
        }

        if (students.size() > 1) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponseDTO(
                            "Existen estudiantes con el mismo nombre",
                            students
                    ));
        }

        return ResponseEntity.ok(
                new ApiResponseDTO(
                        "Registro actualizado correctamente",
                        students
                )
        );
    }

    // 4. Delete por ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseDTO> delete(@PathVariable Long id) {
        try {
            List<Student> students = service.deleteById(id);

            return ResponseEntity.ok(
                    new ApiResponseDTO(
                            "Registro con ID " + id + " eliminado correctamente",
                            students
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseDTO(
                            "No se encontraron registros en BD",
                            null
                    ));
        }
    }
}
