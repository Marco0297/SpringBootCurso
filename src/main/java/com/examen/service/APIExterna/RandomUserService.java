package com.examen.service.APIExterna;

import com.examen.entity.UsuarioEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Service
public class RandomUserService {
    private final WebClient webClient;

    //Inicialiso el webClient del API Externo en config.APIExterna
    public RandomUserService(@Qualifier("api") WebClient webClient) {
        this.webClient = webClient;
    }

    public UsuarioEntity getUserFromAPIByGender(String genero) {
        UsuarioEntity respuesta = new UsuarioEntity(); //Creo el modelo vacio

        ResponseEntity<String> jsonString;
        try {
            jsonString = getAPIResponsByGender(genero).block();
        }catch (Exception e){
            return respuesta; //Si por algo truena que regrese el modelo vacio
        }


        if(jsonString == null){
            return respuesta; //Si no regresa nada regreso el modelo vacio
        }

        String responseJson = jsonString.getBody();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        try {
            root = mapper.readTree(responseJson);
        } catch (JsonProcessingException e) {
            //throw new RuntimeException(e);
            return respuesta; //Si cumple lo regresa vacion
        }

        JsonNode resultsNode = root.path("results").get(0); //Como solo regresa 1 elemento selecciono el primero

        respuesta.setId(1L); //Si tiene 1 me indica que si encontro algo la API
        respuesta.setCorreo(resultsNode.get("email").asText());
        respuesta.setTelefonoCasa(resultsNode.get("phone").asText());
        respuesta.setTelefonoCelular(resultsNode.get("cell").asText());

        JsonNode nombreNode = resultsNode.path("name");
        respuesta.setNombre(nombreNode.get("first").asText());
        respuesta.setApellido(nombreNode.get("last").asText());


        return respuesta;//Si nada traer el onjeto
    }


    private Mono<ResponseEntity<String>> getAPIResponsByGender(String gender){
        return webClient.get()//hacemos peticion de tipo GET
                .uri(uriBuilder -> uriBuilder.queryParam("gender", gender).build())
                .retrieve()//ejecutar la peticion
                .bodyToMono(String.class)//Mono<String>
                .map(response -> ResponseEntity.ok(response));
    }

}
