package mx.com.oscaraparicio.estudiantes.service.impl;

import mx.com.oscaraparicio.estudiantes.dto.ApiResponse;
import mx.com.oscaraparicio.estudiantes.dto.StudentUpdateRequest;
import mx.com.oscaraparicio.estudiantes.dto.randomuser.RandomUserResponse;
import mx.com.oscaraparicio.estudiantes.model.Student;
import mx.com.oscaraparicio.estudiantes.repository.StudentRepository;
import mx.com.oscaraparicio.estudiantes.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;

/**
 * StudentServiceImpl
 * ===================
 * Esta es la implementacion real del contrato/interfaz StudentService
 * SE debe implementar todos los metodos que se tienen en la interfaz
 */
@Service
public class StudentServiceImpl implements StudentService {

    /**
     * final se asigna 1 vez en el constructor (inmutabilidad)
     */
    private final StudentRepository repository;
    private final WebClient randomUserClient;

    /**
     * Random para alternar entre male/female
     */
    private final Random random = new Random();

    /**
     * Constructor (DI por constructor)
     */
    public StudentServiceImpl(StudentRepository repository, WebClient randomUserClient) {
        this.repository = repository;
        this.randomUserClient = randomUserClient;
    }

    // =========================================================
    // 1) REGISTRO MASIVO: consumir API externa "n" veces y guardar
    // =========================================================
    @Override
    public ResponseEntity<ApiResponse> registerFromExternalApi(int n) {

        if (n <= 0) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("El parametro 'registro' debe ser mayor a 0", null));
        }

        try {
            /**
             * Generamos un flujo con n elementos:
             * Por cada elemento (n) hacemos 1 llamada a la API.
             */
            List<Student> studentsToSave = Flux.range(0, n)
                    /**
                     * Por cada i se ejecuta fetchOneRandomUser()
                     * fetchOneRandomUser() regresa Mono<Result>
                     * .flatMap "/quita" todos esos Monos en un solo flujo
                     */
                    .flatMap(i -> fetchOneRandomUser())

                    /**
                     * map:
                     * Convierte el Result (DTO externo) en Student (Entity para BD)
                     */
                    .map(this::mapRandomUserToStudent)

                    /**
                     * collectList:
                     * Junta todos los students del flujo en una List<Student>
                     */
                    .collectList()

                    /**
                     * .block():
                     * Detiene el flujo reactivo y espera el resultado
                     */
                    .block();

            // Si vino null o vacio lo tratamos como error
            if (studentsToSave == null || studentsToSave.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse("No se logro hacer registro por falla de API-randomuser", null));
            }

            // Guardamos en 1 sola operacion
            repository.saveAll(studentsToSave);

            // Respuesta (200)
            return ResponseEntity.ok(
                    new ApiResponse("Se crearon registros en BD correctamente: Numero de registros = " + n, null)
            );

        } catch (Exception e) {
            // Si falla la API
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)//500
                    .body(new ApiResponse(
                            "No se logro hacer registro por una falla de API", null));
        }
    }

    /**
     * fetchOneRandomUser()
     * ==========================
     * Llama a randomuser.me
     * Obtiene 1 usuari@
     * Regresa el "Result" (results[0])
     * Regresa Mono<Result> porque WebClient es reactivo
     */
    private Mono<RandomUserResponse.Result> fetchOneRandomUser() {

        // Random para male/female
        String gender = random.nextBoolean() ? "male" : "female";

        return randomUserClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/")
                        .queryParam("gender", gender)
                        .build())
                .retrieve()

                /**
                 * onStatus:
                 * Si la respuesta HTTP es 4xx o 5xx, lo manejams como error
                 */
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> Mono.error(new RuntimeException("Error HTTP al consumir randomuser"))
                )

                // Convierte el JSON a DTO RandomUserResponse
                .bodyToMono(RandomUserResponse.class)

                // Timeout para que no se quede colgado
                .timeout(Duration.ofSeconds(5))

                /**
                 * Extraeamos results[0]
                 * .flatMap porque podemos validar y lanzar error.
                 */
                .flatMap(this::extractFirstResult);
    }

    /**
     * extractFirstResult()
     * --------------------
     * Toma el DTORandomUserResponse completo y regresa solo results[0]
     */
    private Mono<RandomUserResponse.Result> extractFirstResult(RandomUserResponse response) {

        if (response == null || response.getResults() == null || response.getResults().isEmpty()) {
            return Mono.error(new RuntimeException("randomuser regreso results vacio"));
        }

        return Mono.just(response.getResults().get(0));
    }

    /**
     * mapRandomUserToStudent()
     * ========================
     * Convierte el DTO extreno RandomUser Result a Entity Student
     * para guardar en BD
     */
    private Student mapRandomUserToStudent(RandomUserResponse.Result user) {

        if (user.getName() == null) {
            throw new RuntimeException("Randomuser devolvio name null");
        }

        return new Student(
                user.getName().getFirst(),
                user.getName().getLast(),
                user.getEmail(),
                user.getPhone(),
                user.getCell()
        );
    }

    // =========================================================
    // 2) OBTENER getAll /student/allRegisters
    // =========================================================
    @Override
    public ResponseEntity<ApiResponse> getAll() {

        List<Student> students = repository.findAll();

        // Si no hay registros
        if (students.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("No se encontraron registros en BD", null));
        }

        // Si hay => 200 msg + details
        return ResponseEntity.ok(
                new ApiResponse("Registros encontrados: " + students.size(), students)
        );
    }

    // =========================================================
    // 3) UPDATE POR NOMBRE /student/update/{NOMBRE}
    // =========================================================
    @Override
    public ResponseEntity<ApiResponse> updateByFirstName(String name, StudentUpdateRequest request) {

        List<Student> found = repository.findByFirstNameIgnoreCase(name);

        // Si no existe => 404
        if (found.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("No existen registros con nombre " + name, null));
        }

        // Si hay mas de 1 = 409 (conflict) con details (lista)
        if (found.size() > 1) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new ApiResponse("Existen estudiantes con el mismo nombre: " + found.size(), found));
        }

        // Actualizar si solo hay 1 estudiante
        Student student = found.get(0);

        /**
         * Actualizacion parcial
         * Actualizmos lo que el usuario mande (lo que no queda null)
         */
        if (request.getLastName() != null) student.setLastName(request.getLastName());
        if (request.getEmail() != null) student.setEmail(request.getEmail());
        if (request.getPhone() != null) student.setPhone(request.getPhone());
        if (request.getCell() != null) student.setCell(request.getCell());

        Student updated = repository.save(student);

        // 200 con details mostrando el registro actualizado
        return ResponseEntity.ok(
                new ApiResponse("Registro actualizado correctamente", List.of(updated))
        );
    }

    // =========================================================
    // 4) DELETE POR ID: /student/delete/{id}
    // =========================================================
    @Override
    public ResponseEntity<ApiResponse> deleteById(Long id) {

        // Si no existe 404
        if (!repository.existsById(id)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("No se encontraron registros en BD", null));
        }

        // Si existe lo borramos
        repository.deleteById(id);

        // Todos los registros actuales
        List<Student> current = repository.findAll();

        return ResponseEntity.ok(
                new ApiResponse("Registro con ID " + id + ", eliminado correctamente", current)
        );
    }
}
