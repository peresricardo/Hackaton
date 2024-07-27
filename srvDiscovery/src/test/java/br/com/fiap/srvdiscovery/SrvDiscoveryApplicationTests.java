package br.com.fiap.srvdiscovery;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class SrvDiscoveryApplicationTests {

    @Test
    public void iniciar_aplicacao_sucesso() {
        String[] args = {};
        assertDoesNotThrow(() -> SrvDiscoveryApplication.main(args));
    }

}
