package edu.msudenver;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(MainApplication.class)
public class TestConfig {

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return Mockito.mock(RestTemplateBuilder.class);
    }
}