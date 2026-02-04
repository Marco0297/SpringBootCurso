package com.sistemagestionestudiantes.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuracion para que apunte a randomuserApi
 * es una api externa
 */
@Configuration
public class WebClientConfig {

    private static String URI_USER_RANDOM = "https://randomuser.me/api/";

    /**
     * Se configuró un Bean de WebClient
     * con la URL base de la API externa para permitir su inyección
     * y reutilización dentro del sistema
     * */
    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl(URI_USER_RANDOM).build();
    }
  }
