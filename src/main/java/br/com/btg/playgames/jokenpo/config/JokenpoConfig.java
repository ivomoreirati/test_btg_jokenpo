package br.com.btg.playgames.jokenpo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class JokenpoConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
