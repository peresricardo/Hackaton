package br.com.fiap.srvcliente.service;

import br.com.fiap.srvcliente.dto.ClienteDto;
import br.com.fiap.srvcliente.exception.MensagemNotFoundException;
import br.com.fiap.srvcliente.model.ClienteModel;
import br.com.fiap.srvcliente.repository.ClienteRepository;
import br.com.fiap.srvcliente.service.serviceImpl.ClienteServiceImpl;
import br.com.fiap.srvcliente.utils.ClienteHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;


public class ClienteServiceTest {
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {

        openMocks = MockitoAnnotations.openMocks(this);
        clienteService = new ClienteServiceImpl(clienteRepository);
    }

    @AfterEach
    void tearDow() throws Exception {
        openMocks.close();
    }

    @Nested
    class RegistrarCliente {
        @Test
        void devePermitirCadastrarCliente() {
            var cliente = ClienteHelper.gerarRegistro();
            var dto = ClienteDto.fromEntity(cliente);

            when(clienteRepository.save(any(ClienteModel.class)))
                    .thenAnswer(i -> i.getArgument(0));

            var clienteRegistrado = clienteService.cadastrarCliente(dto);

            assertThat(clienteRegistrado).isInstanceOf(ClienteDto.class).isNotNull();
            assertThat(clienteRegistrado.nome()).isEqualTo(cliente.getNome());
            assertThat(clienteRegistrado.email()).isEqualTo(cliente.getEmail());
            verify(clienteRepository, times(1)).save(any(ClienteModel.class));
        }
    }

    @Nested
    class AlterarCliente {
        @Test
        void devePermitirAlterarCliente() {
            var cliente = ClienteHelper.gerarRegistro();
            var dto = ClienteDto.fromEntity(cliente);
            when(clienteRepository.existsByCpf(dto.cpf())).thenReturn(true);
            when(clienteRepository.save(any(ClienteModel.class))).thenReturn(cliente);

            var resultado = clienteService.atualizarCliente(dto);

            assertThat(resultado).isInstanceOf(ClienteDto.class);
            assertNotNull(resultado);
            assertThat(resultado.cpf()).isEqualTo(cliente.getCpf());
            verify(clienteRepository, times(1)).save(any(ClienteModel.class));
        }

        @Test
        void naoDevePermitirAlterarCliente() {
            var cliente = ClienteHelper.gerarRegistro();
            cliente.setCpf("99999999999");
            var dto = ClienteDto.fromEntity(cliente);
            when(clienteRepository.existsByCpf(dto.cpf())).thenReturn(false);

            assertThrows(MensagemNotFoundException.class, () -> clienteService.atualizarCliente(dto));
            verify(clienteRepository, times(1)).existsByCpf(any(String.class));
        }
    }

    @Nested
    class RemoverCliente {
        @Test
        void devePermitirRemoverCliente() {
            var cliente = ClienteHelper.gerarRegistro();
            var dto = ClienteDto.fromEntity(cliente);
            when(clienteRepository.existsByCpf(dto.cpf())).thenReturn(true);
            when(clienteRepository.findByCpf(dto.cpf())).thenReturn(cliente);

            var resultado = clienteService.excluirCliente(dto);

            assertTrue(resultado);
            verify(clienteRepository, times(1)).delete(any(ClienteModel.class));
        }

        @Test
        void naoDevePermitirRemoverCliente() {
            var cliente = ClienteHelper.gerarRegistro();
            cliente.setCpf("22222222222");
            var dto = ClienteDto.fromEntity(cliente);
            when(clienteRepository.existsByCpf(dto.cpf())).thenReturn(false);

            assertThrows(MensagemNotFoundException.class, () -> clienteService.excluirCliente(dto));
        }
    }

    @Nested
    class ListarClientes {
        @Test
        void devePermitirListarClientes() {
            var reg1 = ClienteHelper.gerarRegistro();
            var reg2 = ClienteHelper.gerarRegistro();
            Page<ClienteModel> lista = new PageImpl<>(Arrays.asList(reg1, reg2));
            when(clienteRepository.findAll(any(Pageable.class))).thenReturn(lista);

            var listaRecebida = clienteService.listarClientes(Pageable.unpaged());

            assertThat(listaRecebida).hasSizeGreaterThan(1);
            verify(clienteRepository, times(1)).findAll(any(Pageable.class));
        }
    }

    @Nested
    class BuscarCliente {
        @Test
        void devePermitirBuscarClientePorCpf() {
            String cpf = "11111111111";
            var cliente = ClienteHelper.gerarRegistro();
            when(clienteRepository.existsByCpf(cpf)).thenReturn(true);
            when(clienteRepository.findByCpf(cpf)).thenReturn(cliente);

            var resultado = clienteService.buscarClientePorCpf(cpf);

            assertThat(cpf).isEqualTo(resultado.cpf());
            verify(clienteRepository, times(1)).findByCpf(cliente.getCpf());
        }

        @Test
        void deveGerarExcecaoAoBuscarClientePorCpf_NaoEncontrado() {
            var cpf = "99999999999";
            when(clienteRepository.existsByCpf(cpf)).thenReturn(false);

            assertThrows(MensagemNotFoundException.class,
                    () -> {clienteService.buscarClientePorCpf(cpf);
                    });
        }

        @Test
        void devePermitirValidarClienteExistePorCpf_RetornarTrue() {
            var cpf = "11111111111";
            when(clienteRepository.existsByCpf(cpf)).thenReturn(true);

            var resultado = clienteService.existeClientePorCpf(cpf);

            assertThat(resultado).isTrue();
            verify(clienteRepository, times(1)).existsByCpf(cpf);
        }

        @Test
        void devePermitirValidarClienteExistePorCpf_RetornarFalse() {
            String cpf = null;
            when(clienteRepository.existsByCpf(cpf)).thenReturn(false);

            var resultado = clienteService.existeClientePorCpf(cpf);

            assertFalse(resultado);
            verify(clienteRepository, times(1)).existsByCpf(cpf);
        }
    }

}
