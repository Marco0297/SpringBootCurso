package com.personas.service;

import com.personas.model.EstudianteModel;
import com.personas.response.ResponseApiRecordDetails;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetRandomStudentService {

    private final WebClient webClient;

    //Se inyecta la dependencia del webclient mediante constructor
    public GetRandomStudentService(@Qualifier("userRandom") WebClient webClient) {
        this.webClient = webClient;
    }

    public ResponseEntity<ResponseApiRecordDetails> getRandomStudent(int numberStudents) {
        ResponseEntity<String> estudent = null;
        try {
            estudent = getEstudent(numberStudents).block();
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(new ResponseApiRecordDetails("500", new ArrayList<>()));
        }

        if (estudent == null) {
            ResponseApiRecordDetails error = new ResponseApiRecordDetails("No se pudieron generar estudiantes", new ArrayList<>());
            return ResponseEntity.internalServerError().body(error);
        }

        String responseJson = estudent.getBody();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(responseJson);
        JsonNode results = root.path("results");

        if (results != null && results.size() > 0) {

            List<EstudianteModel> detailsList = new ArrayList<>();
            results.iterator().forEachRemaining(result -> {
                JsonNode nameNode  = result.path("name");
                String first = nameNode.get("first").asText();
                String last = nameNode.get("last").asText();

                String email = result.get("email").asText();
                String phone = result.get("phone").asText();
                String cell = result.get("cell").asText();

                EstudianteModel estudentDetail = new EstudianteModel();
                estudentDetail.setFirst(first);
                estudentDetail.setLast(last);
                estudentDetail.setEmail(email);
                estudentDetail.setPhone(phone);
                estudentDetail.setCell(cell);

                detailsList.add(estudentDetail);

            });

            ResponseApiRecordDetails responseApiRecord = new ResponseApiRecordDetails("Se crearon registros en BD correctamente: Numero de registros : " + detailsList.size(),detailsList);

            return ResponseEntity.ok(responseApiRecord);
        }else {
            ResponseApiRecordDetails error = new ResponseApiRecordDetails("El nodo results no existe", new ArrayList<>());
            return ResponseEntity.internalServerError().body(error);
        }
    }

    public Mono<ResponseEntity<String>> getEstudent(int numberStudents) {
        return webClient.get()//se realiza la peticion al end point
                .uri(uriBuilder -> uriBuilder.queryParam("results", numberStudents).build())//se forma la url
                .retrieve()//ejecuta la peticion
                .bodyToMono(String.class)
                .map(response -> ResponseEntity.ok().body(response)); //Al final retornamos el objeto
    }
}
