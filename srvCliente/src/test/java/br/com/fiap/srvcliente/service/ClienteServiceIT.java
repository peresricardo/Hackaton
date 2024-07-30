package br.com.fiap.srvcliente.service;

import br.com.fiap.srvcliente.dto.ClienteDto;
import br.com.fiap.srvcliente.utils.ClienteHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class ClienteServiceIT {
    @Autowired
    private ClienteService service;

    @Nested
    class RegistrarCliente {
        @Test
        void devePermitirCadastrarCliente() {
            var entity = ClienteHelper.gerarRegistro();
            var dto = ClienteDto.fromEntity(entity);

            var resultado = service.cadastrarCliente(dto);

            assertThat(resultado).isNotNull();
            assertThat(resultado.cpf())
                    .isNotNull()
                    .isEqualTo(dto.cpf());
        }
    }

    @Nested
    class AlterarCliente {
        @Test
        void devePermitirAlterarCliente() {
            var entity = ClienteHelper.gerarRegistro();
            var dto = ClienteDto.fromEntity(entity);
            service.cadastrarCliente(dto);

            var resultado = service.atualizarCliente(dto);

            assertThat(resultado).isNotNull();
            assertThat(resultado.cpf())
                    .isNotNull()
                    .isNotEmpty()
                    .isEqualTo(dto.cpf());
            assertThat(resultado.rua())
                    .isNotNull()
                    .isNotEmpty()
                    .isEqualTo(dto.rua());
        }
    }

    @Nested
    class RemoverCliente {
        @Test
        void devePermitirRemoverCliente() {
            var entity = ClienteHelper.gerarRegistro();
            var dto = ClienteDto.fromEntity(entity);
            service.cadastrarCliente(dto);

            var resultado = service.excluirCliente(dto);

            assertThat(resultado).isTrue();
        }
    }

    @Nested
    class ListarClientes {
        @Test
        void devePermitirListarClientes() {
            var entity1 = ClienteHelper.gerarRegistro();
            var entity2 = ClienteHelper.gerarRegistro();
            entity2.setCpf("22222222222");
            var dto1 = ClienteDto.fromEntity(entity1);
            var dto2 = ClienteDto.fromEntity(entity2);
            service.cadastrarCliente(dto1);
            service.cadastrarCliente(dto2);

            var lista = service.listarClientes(Pageable.unpaged());

            assertThat(lista).isNotNull();
            assertThat(lista).hasSize(2);
        }
    }

    @Nested
    class BuscarCliente {
        @Test
        void devePermitirBuscarClientePorCpf() {
            var entity = ClienteHelper.gerarRegistro();
            var dto = ClienteDto.fromEntity(entity);
            var cpf = "11111111111";
            service.cadastrarCliente(dto);

            var resultado = service.buscarClientePorCpf(cpf);

            assertThat(resultado).isNotNull();
            assertThat(resultado.cpf())
                    .isNotNull()
                    .isNotEmpty()
                    .isEqualTo(cpf);
        }

        @Test
        void existeClientePorCpf() {
            var entity = ClienteHelper.gerarRegistro();
            var dto = ClienteDto.fromEntity(entity);
            var cpf = "11111111111";
            service.cadastrarCliente(dto);

            var resultado = service.existeClientePorCpf(cpf);

            assertTrue(resultado);
        }

        @Test
        void naoDeveExistirClientePorCpf() {
            var cpf = "11111111111";

            var resultado = service.existeClientePorCpf(cpf);

            assertFalse(resultado);
        }
    }
}
