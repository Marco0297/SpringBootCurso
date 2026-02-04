package com.sistemagestionestudiantes.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistemagestionestudiantes.model.StudentsModel;
import com.sistemagestionestudiantes.repository.StudentsRepository;
import com.sistemagestionestudiantes.response.ResponseApiDetail;
import com.sistemagestionestudiantes.response.ResponseApiRecord;
import com.sistemagestionestudiantes.response.StudentDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;


@Service
public class RandomStudentService {


    private WebClient webClient;
    private StudentsRepository studentsRepository;

    //Inyección de las dependencias por constructor
    public RandomStudentService(WebClient webClient, StudentsRepository studentsRepository) {
        this.webClient = webClient;
        this.studentsRepository = studentsRepository;
    }


    // Crea los estudiantes y los guarda en la base de datos
    public ResponseEntity<ResponseApiRecord> creaStudent(Integer n) throws JsonProcessingException {

        // Se coloca el mapper para convertir los objetos java a JSON
        ObjectMapper mapper = new ObjectMapper();
        int estudianteguardado = 0;

    try{
        for (int i=0; i < n; i++){
        // Consumimos el servicio y lo guardamos el resultado en un JSON
        String responseJson = webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.queryParam("gender", "male").build())
                .retrieve()//ejecutar la peticion
                .bodyToMono(String.class)//Mono<String>
                .block();

        // Creamos la logica para convetir la respuesta en una JSON e ingresar a el nodo results
        JsonNode root = mapper.readTree(responseJson);
        JsonNode user = root.path("results").get(0);

        // Seteamos los valores del JSON del response en las variables del Model y lo guardamos en el repository

            StudentsModel estudiante = new StudentsModel();
            estudiante.setFirstName(user.path("name").path("first").asText());
            estudiante.setLastName(user.path("name").path("last").asText());
            estudiante.setEmail(user.path("email").asText());
            estudiante.setPhone(user.path("phone").asText());
            estudiante.setCell(user.path("cell").asText());

            studentsRepository.save(estudiante);
            estudianteguardado++;
        }
        ResponseApiRecord responseApiRecord = new ResponseApiRecord("Se crearon registros en BD correctamente: Numero de registros: "+estudianteguardado);
        return ResponseEntity.status(200)
                .body(responseApiRecord);

    } catch (JsonProcessingException e) {
        ResponseApiRecord responseApiRecord = new ResponseApiRecord("No se logro hacer registro por falla de API-users");
            return ResponseEntity.status(500)
                    .body(responseApiRecord);
        }
    }


    // Obtiene o visualiza todos los registros guardados en BD

    public ResponseEntity<ResponseApiDetail> getAllStudents() {

        // Se declara la lista estudiantes en base al modelo y lo que nos recupera el repository
        List<StudentsModel> estudiantes = studentsRepository.findAll();

        // Se crea el StudentDetails cómo una lista de array
        List<StudentDetail> studentDetails = new ArrayList<>();

//  recorremos la lista de estudiantes del repositorio y los agregamos al studentdetail
        for (StudentsModel e : estudiantes) {
            StudentDetail detail = new StudentDetail(
                    e.getId(),
                    e.getFirstName(),
                    e.getLastName(),
                    e.getEmail(),
                    e.getPhone(),
                    e.getCell()
            );
            studentDetails.add(detail);
        }

        // Si la lista esta vacia, marcamos el mensaje y regresamos el estatus
        if (estudiantes.isEmpty()) {
            ResponseApiDetail responseApiDetail = new ResponseApiDetail("No se encontraron registros en BD",null);

            return ResponseEntity.status(404)
                    .body(responseApiDetail);
        }

        // Si la lista no esta vacia, tomamos el tamaño de la lista recuperada y mostramos el arraylist que le pasamos
        ResponseApiDetail responseApiDetail = new ResponseApiDetail("Registros encontrados: " + estudiantes.size(),studentDetails);

        return ResponseEntity.status(200)
                .body(responseApiDetail);
    }

    // Actualización de un registro por nombre

    // StudentsModel es el cuerpo que requerimos de postman para recuperar los nuevos datos
    public ResponseEntity<ResponseApiDetail> updateStudent(String nombre, StudentsModel studentsModel) {

        // Creamos la lista students donde al colocar la condición del repositorio coincida con el nombre
        List<StudentsModel> students =  studentsRepository.findAllByFirstName(nombre);


        // Si el estudiante esta vació = lista vacía no existen registros
        if (students.isEmpty()) {
            ResponseApiDetail responseApiDetail = new ResponseApiDetail("No existen registros con nombre" + nombre,null);
            return ResponseEntity.status(400)
                    .body(responseApiDetail);
        }else {

            // Si el tamaño de la lista recuperada del nombre es mayor a 1 es que hay varios studiantes con el mismo nombre
            if (students.size() > 1){

                // Misma logica para crear la lista de array
                List<StudentDetail> studentDetails = new ArrayList<>();

            //  Se recorre la lista de estudiantes y lo agregamos a StudentDetail
                for (StudentsModel e : students) {
                    StudentDetail detail = new StudentDetail(
                            e.getId(),
                            e.getFirstName(),
                            e.getLastName(),
                            e.getEmail(),
                            e.getPhone(),
                            e.getCell()
                    );
                    studentDetails.add(detail);
                }
                ResponseApiDetail responseApiDetail = new ResponseApiDetail("Existen estudiantes con el mismo nombre" + students.size(),studentDetails);
                return ResponseEntity.status(409)
                        .body(responseApiDetail);

            }else {
// De lo contrario seteamos que el primer elemento de la lista lo tome
                StudentsModel studentsModel1 = students.get(0);
                // Del primer elemento de la lista seteamos los datos recibidos
                studentsModel1.setFirstName(studentsModel.getFirstName());
                studentsModel1.setLastName(studentsModel.getLastName());
                studentsModel1.setEmail(studentsModel.getEmail());
                studentsModel1.setPhone(studentsModel.getPhone());
                studentsModel1.setCell(studentsModel.getCell());

// saveAndFlush() es un método de Spring Data JPA que sirve para guardar (insertar o actualizar) un objeto en la base de datos.
                StudentsModel studentUpdate = studentsRepository.saveAndFlush(studentsModel1);
                // Carmamos la lista studentDetail con el unico registro con los datos actualizados
                StudentDetail detail = new StudentDetail(
                        studentsModel1.getId(),
                        studentsModel1.getFirstName(),
                        studentsModel1.getLastName(),
                        studentsModel1.getEmail(),
                        studentsModel1.getPhone(),
                        studentsModel1.getCell()
                );

                // Se responde con la lista creada anteriormente del usuario actualizado
                ResponseApiDetail responseApiDetail = new ResponseApiDetail("Registro actualizado",List.of(detail));
                return ResponseEntity.status(200)
                        .body(responseApiDetail);
            }
        }
    }


    // Eliminación de un registro por el ID y mostrar el listado de los Estudiantes que quedan

    public ResponseEntity<ResponseApiDetail> deleteStudentById(Long id) {

        if (studentsRepository.existsById(id)) {
            // Si existe lo elimina de la base de datos temporal
            studentsRepository.deleteById(id);

            // Se sigue la logica de traer todos los registros
            List<StudentsModel> estudiantes = studentsRepository.findAll();
            List<StudentDetail> studentDetails = new ArrayList<>();

            //  Se recorre la lista de estudiantes y lo agregamos a StudentDetail
            for (StudentsModel e : estudiantes) {
                StudentDetail detail = new StudentDetail(
                        e.getId(),
                        e.getFirstName(),
                        e.getLastName(),
                        e.getEmail(),
                        e.getPhone(),
                        e.getCell()
                );
                studentDetails.add(detail);
            }


            ResponseApiDetail responseApiDetail = new ResponseApiDetail("Registro con ID " + id + ", eliminado correctamente", studentDetails);

            return ResponseEntity.status(200)
                    .body(responseApiDetail);

              }else {

            ResponseApiDetail responseApiDetail = new ResponseApiDetail("No se encontraron registros en BD", null);

            // Si no existe el ID del estudiante
            return ResponseEntity.status(404)
                    .body(responseApiDetail);

        }

    }

}
