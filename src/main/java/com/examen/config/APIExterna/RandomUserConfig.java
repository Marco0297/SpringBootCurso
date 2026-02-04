package com.examen.config.APIExterna;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RandomUserConfig {

    private static String URI_API = "https://randomuser.me/api/";

    @Bean(value = "api")
    public WebClient webClient() {
        return WebClient.builder().baseUrl(URI_API).build();
    }
}
