package br.com.fiap.srvCartao;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

class SrvCartaoApplicationTest {

    @Test
    public void main() {
        try (MockedStatic<SpringApplication> mocked = mockStatic(SpringApplication.class)) {
            mocked.when(() -> SpringApplication.run(SrvCartaoApplication.class, new String[]{}))
                    .thenReturn(mock(ConfigurableApplicationContext.class));

            SrvCartaoApplication.main(new String[]{});

            mocked.verify(() -> SpringApplication.run(SrvCartaoApplication.class, new String[]{}));
        }
    }

}