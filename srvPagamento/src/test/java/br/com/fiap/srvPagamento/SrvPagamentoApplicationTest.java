package br.com.fiap.srvPagamento;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

class SrvPagamentoApplicationTest {

    @Test
    public void main() {
        try (MockedStatic<SpringApplication> mocked = mockStatic(SpringApplication.class)) {
            mocked.when(() -> SpringApplication.run(SrvPagamentoApplication.class, new String[]{}))
                    .thenReturn(mock(ConfigurableApplicationContext.class));

            SrvPagamentoApplication.main(new String[]{});

            mocked.verify(() -> SpringApplication.run(SrvPagamentoApplication.class, new String[]{}));
        }
    }
}