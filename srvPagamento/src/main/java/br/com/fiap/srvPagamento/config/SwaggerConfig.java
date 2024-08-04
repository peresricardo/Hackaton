package br.com.fiap.srvPagamento.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("Server Development");


        Contact contact = new Contact();
        contact.setEmail("contato@gmail.com");
        contact.setName("Contato");

        Info info = new Info()
                .title("Hackathon FIAP Pos-Tech - Serviço Pagamento")
                .version("1.0")
                .contact(contact)
                .description("Documentação dos endpoints da Hackathon FIAP Pos-Tech");


        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}