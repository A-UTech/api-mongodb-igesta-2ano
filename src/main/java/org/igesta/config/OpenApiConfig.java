package org.igesta.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("API MONGODB iGesta")
                        .version("1.0")
                        .description("API para contagem de condenas do app iGesta")
                        .termsOfService("https://www.exemplo.com/terms")
                        .contact(new Contact()
                                .name("Equipe de Desenvolvimento")
                                .email("autechsegundao@gmail.com")
                                .url("https://www.landingpage.com")) //Adicionar posteriormente o link da landingPage
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")));
    }
}
