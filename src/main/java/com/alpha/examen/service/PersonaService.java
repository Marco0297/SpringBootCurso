package com.alpha.examen.service;

import com.alpha.examen.model.PersonaModel;
import com.alpha.examen.repository.PersonaRepository;
import com.alpha.examen.response.ResponseApiRecord;
import com.alpha.examen.response.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PersonaService implements IPersona {

    @Autowired
    @Qualifier("userRandom")
    private WebClient webClient;

    @Autowired
    private PersonaRepository personaRepository;


    @Override
    public ResponseEntity<ResponseApiRecord> createPerson(int cantidad) {
        if(cantidad<=0){
            return ResponseEntity.badRequest().body(new ResponseApiRecord("La cantidad debe ser mayor a cero", List.of()));
        }

        try{
            List<UserDetails> detailsList = new ArrayList<>();
            List<PersonaModel> personaModels = new ArrayList<>();

            for(int i=0; i<cantidad; i++){

                String genero = Math.random() < 0.5 ? "male" : "female";
                ResponseEntity<String> obtenerGender = obtenerGenderWithLamnbda(genero).block();

                if(obtenerGender == null || obtenerGender.getBody().isEmpty()){
                    return ResponseEntity.internalServerError().body(new ResponseApiRecord("El API regreso informaciòn vacia", List.of()));
                }

                String responseJson = obtenerGender.getBody();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(responseJson);
                JsonNode results = root.path("results").path(0);

                if (results != null && results.size() > 0) {
                    String firstName = results.path("name").path("first").asText(null);
                    String lastName = results.path("name").path("last").asText(null);
                    String email = results.path("email").asText(null);
                    String phone = results.path("phone").asText(null);
                    String cell = results.path("cell").asText(null);
                    String gender = results.path("gender").asText(null);

                    PersonaModel personaModel = new PersonaModel(firstName,lastName,email,phone,cell, gender);
                    personaModels.add(personaModel);

                    UserDetails userDetails = new UserDetails(firstName,lastName,email,phone,cell, gender);
                    detailsList.add(userDetails);

                }else {
                    return ResponseEntity.internalServerError().body(new ResponseApiRecord("El nodo results regreso vacio", List.of()));
                }
            }


            personaRepository.saveAll(personaModels);

            ResponseApiRecord responseApiRecord = new ResponseApiRecord("Se crearon registros en BD correctamente: Número de registros :" + detailsList.size(), detailsList);
            return ResponseEntity.ok(responseApiRecord);


        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ResponseApiRecord("No se logro hacer registro por falla de API-users", List.of()));
        }

    }

    public Mono<ResponseEntity<String>> obtenerGenderWithLamnbda(String gender){
        return webClient.get()//hacemos peticion de tipo GET
                .uri(uriBuilder -> uriBuilder.queryParam("gender", gender).build())
                .retrieve()//ejecutar la peticion
                .bodyToMono(String.class)//Mono<String>
                .map(response -> ResponseEntity.ok(response));
    }
}
