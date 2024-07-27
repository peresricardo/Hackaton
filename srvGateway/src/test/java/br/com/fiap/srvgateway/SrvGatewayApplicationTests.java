package br.com.fiap.srvgateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SrvGatewayApplicationTests {

    // Application starts successfully with valid arguments
    @Test
    public void inicia_aplicacao_sucesso() {
        String[] args = {"--server.port=8080"};
        SrvGatewayApplication.main(args);
    }
}
