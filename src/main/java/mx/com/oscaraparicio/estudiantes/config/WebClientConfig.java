package mx.com.oscaraparicio.estudiantes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient randomUserClient(WebClient.Builder builder){
        return builder
                .baseUrl("https://randomuser.me")
                .build();
    }
}
