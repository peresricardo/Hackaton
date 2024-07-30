package br.com.fiap.srvcliente.repository;

import br.com.fiap.srvcliente.model.ClienteModel;
import br.com.fiap.srvcliente.utils.ClienteHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClienteRepositoryTest {
    @Mock
    private ClienteRepository repository;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class RegistrarCliente {
        @Test
        void devePermitirRegistrarCliente() {
            // Arrange
            var registro = ClienteHelper.gerarRegistro();
            when(repository.save(any(ClienteModel.class))).thenReturn(registro);
            // Action
            var registroArmazenado = repository.save(registro);
            // Assert
            assertThat(registroArmazenado).isNotNull().isEqualTo(registro);
            verify(repository, times(1)).save(any(ClienteModel.class));
        }
    }

    @Nested
    class RemoverCliente {
        @Test
        void devePermitirRemoverCliente() {
            // Arrange
            var registro = ClienteHelper.gerarRegistro();
            doNothing().when(repository).delete(registro);
            // Act
            repository.delete(registro);
            // Assert
            verify(repository, times(1)).delete(any(ClienteModel.class));
        }
    }

    @Nested
    class ListarClientes {
        @Test
        void devePermitirListarClientes() {
            // Arrange
            var reg1 = ClienteHelper.gerarRegistro();
            var reg2 = ClienteHelper.gerarRegistro();
            var lista = Arrays.asList(reg1, reg2);
            when(repository.findAll()).thenReturn(lista);
            // Action
            var listaRecebida = repository.findAll();
            // Assert
            assertThat(listaRecebida).hasSizeGreaterThan(1);
            verify(repository, times(1)).findAll();
        }
    }

    @Nested
    class BuscarCliente {
        @Test
        void devePermitirBuscarClientePorCpf() {
            // Arrange
            var registro = ClienteHelper.gerarRegistro();
            when(repository.findByCpf(registro.getCpf())).thenReturn(registro);
            // Act
            var entity = repository.findByCpf(registro.getCpf());
            // Assert
            assertThat(entity).isNotNull().isEqualTo(registro);
            verify(repository, times(1)).findByCpf(registro.getCpf());
        }

        @Test
        void deveValidarBuscarClientePorCpf() {
            // Arrange
            var registro = ClienteHelper.gerarRegistro();
            when(repository.existsByCpf(registro.getCpf())).thenReturn(any(Boolean.class));
            // Act
            var entity = repository.existsByCpf(registro.getCpf());
            // Assert
            assertThat(entity).isFalse();
            verify(repository, times(1)).existsByCpf(registro.getCpf());
        }
    }

}
