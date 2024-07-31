package br.com.fiap.srvgateway;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MainAppTest {
    @Test
    public void main(){
        try (MockedStatic<SpringApplication> mocked = Mockito.mockStatic(SpringApplication.class)) {
            mocked.when(() -> SpringApplication.run(SrvGatewayApplication.class, new String[]{}))
                    .thenReturn(Mockito.mock(ConfigurableApplicationContext.class));

            SrvGatewayApplication.main(new String[]{});

            mocked.verify(() -> SpringApplication.run(SrvGatewayApplication.class, new String[]{}));
        }
    }

    @Test
    public void validarTestApp() {
        String[] args = {};
        assertDoesNotThrow(() -> SrvGatewayApplication.main(args));
    }
}
