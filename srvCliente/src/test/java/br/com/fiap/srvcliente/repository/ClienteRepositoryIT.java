package br.com.fiap.srvcliente.repository;

import br.com.fiap.srvcliente.model.ClienteModel;
import br.com.fiap.srvcliente.utils.ClienteHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class ClienteRepositoryIT {
    @Autowired
    private ClienteRepository repository;

    @Nested
    class RegistrarCliente {
        @Test
        void devePermitirCadastrarCliente() {
            var entity = ClienteHelper.gerarRegistro();

            var resultado = ClienteHelper.RegistrarCliente(entity, repository);

            assertThat(resultado)
                    .isInstanceOf(ClienteModel.class)
                    .isNotNull();
        }
    }

    @Nested
    class RemoverCliente {
        @Test
        void devePermitirRemoverCliente() {
            var entity = ClienteHelper.gerarRegistro();
            ClienteHelper.RegistrarCliente(entity, repository);

            repository.delete(entity);
            var resultado = repository.findByCpf("11111111111");

            assertThat(resultado).isNull();
        }
    }

    @Nested
    class ListarClientes {
        @Test
        void devePermitirListarClientes() {
            var entity1 = ClienteHelper.gerarRegistro();
            var entity2 = ClienteHelper.gerarRegistro();
            entity2.setCpf("11111111111");
            ClienteHelper.RegistrarCliente(entity1, repository);
            ClienteHelper.RegistrarCliente(entity2, repository);

            var lista = repository.findAll();

            assertThat(lista).hasSizeGreaterThanOrEqualTo(1);
        }
    }

    @Nested
    class BuscarCliente {
        @Test
        void devePermitirBuscarClientePorCpf() {
            var entity = ClienteHelper.gerarRegistro();
            ClienteHelper.RegistrarCliente(entity, repository);

            var resultado = repository.findByCpf("11111111111");

            assertThat(resultado).isNotNull();
            assertThat(resultado.getCpf()).isEqualTo("11111111111");
        }

        @Test
        void existeClientePorCpf() {
            var entity = ClienteHelper.gerarRegistro();
            ClienteHelper.RegistrarCliente(entity, repository);

            var resultado = repository.existsByCpf("11111111111");

            assertThat(resultado).isTrue();
        }
    }
}
