package mx.com.oscaraparicio.estudiantes.controller;

import mx.com.oscaraparicio.estudiantes.dto.ApiResponse;
import mx.com.oscaraparicio.estudiantes.dto.StudentUpdateRequest;
import mx.com.oscaraparicio.estudiantes.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * StudentController
 * =================
 * Controlador REST:
 * - Expone endpoints HTTP
 * - Recibe parametros del usuario path/query/body
 * - Llama a los servicios
 * - Regresa ResponseEntity con status + body
 */
@RestController
@RequestMapping("/student") // prefijo para todos los endpoints
public class StudentController {

    /**
     * Inyectamos el contarto (interfaz) StudentService
     * final = se asigna 1 vez en constructor
     */
    private final StudentService service;

    /**
     * Constructor
     */

    public StudentController(StudentService service) {
        this.service = service;
    }

    // =========================================================
    // 1) REGISTRO MASIVO:
    // GET  localhost:8080/student/registro?registro=n
    // =========================================================

    @GetMapping("/registro")
    public ResponseEntity<ApiResponse> register(@RequestParam(name = "registro") int n) {

        return service.registerFromExternalApi(n);
    }

    // =========================================================
    // 2) OBTENER TODOS:
    // GET localhost:8080/student/allRegisters
    // =========================================================
    @GetMapping("/allRegisters")
    public ResponseEntity<ApiResponse> allRegisters() {

        return service.getAll();
    }

    // =========================================================
    // 3) UPDATE POR NOMBRE:
    // PUT localhost:8080/student/update/{NOMBRE}
    // Body JSON: StudentUpdateRequest
    // =========================================================

    @PutMapping("/update/{name}")
    public ResponseEntity<ApiResponse> updateByName(@PathVariable String name, @RequestBody StudentUpdateRequest request) {

        return service.updateByFirstName(name, request);
    }

    // =========================================================
    // 4) DELETE POR ID:
    // DELETE localhost:8080/student/delete/{id}
    // =========================================================
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {

        return service.deleteById(id);
    }
}
