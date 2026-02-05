package mx.com.oscaraparicio.estudiantes.service;

import mx.com.oscaraparicio.estudiantes.dto.ApiResponse;
import mx.com.oscaraparicio.estudiantes.dto.StudentUpdateRequest;
import org.springframework.http.ResponseEntity;

/**
 * StudentService (INTERFAZ/CONTRATO)
 * =========================
 */
public interface StudentService {

    /**
     * Registrar N estudiantes consumiendo API externa RandmUser
     */
    ResponseEntity<ApiResponse> registerFromExternalApi(int n);

    /**
     * 2) Obtener todos los registros
     */
    ResponseEntity<ApiResponse> getAll();

    /**
     * 3) Actualizar por nombre (firstName).
     */
    ResponseEntity<ApiResponse> updateByFirstName(String name, StudentUpdateRequest request);

    /**
     * 4) Eliminar por ID.
     */
    ResponseEntity<ApiResponse> deleteById(Long id);
}
