package com.personas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    private static String URI_USER_RANDOM = "https://randomuser.me/api/";

    @Bean(value = "userRandom")
    public WebClient webClient() {
        return WebClient.builder().baseUrl(URI_USER_RANDOM).build();
    }

}
