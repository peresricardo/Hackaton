package br.com.fiap.srvcliente.controller;

import br.com.fiap.srvcliente.dto.ClienteDto;
import br.com.fiap.srvcliente.exception.GlobalExceptionHandler;
import br.com.fiap.srvcliente.service.ClienteService;
import br.com.fiap.srvcliente.utils.ClienteHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ClienteControllerTest {
    private MockMvc mockMvc;

    AutoCloseable openMocks;

    @Mock
    private ClienteService clienteService;
    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        clienteController = new ClienteController(clienteService);
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }


    @Nested
    class RegistrarCliente {
        @Test
        void deveRegistrarCliente() throws Exception {
            var cliente = ClienteHelper.gerarRegistro();
            var dto = ClienteDto.fromEntity(cliente);
            when(clienteService.cadastrarCliente(dto)).thenReturn(dto);

            ResponseEntity<ClienteDto> response = clienteController.cadastrar(dto);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(dto, response.getBody());
        }
    }

    @Nested
    class AlterarCliente {
        @Test
        void deveAlterarCliente() throws Exception {
            var cliente = ClienteHelper.gerarRegistro();
            var dto = ClienteDto.fromEntity(cliente);
            when(clienteService.atualizarCliente(dto)).thenReturn(dto);

            ResponseEntity<ClienteDto> response = clienteController.alterarCliente(dto);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(dto, response.getBody());
        }
    }

    @Nested
    class RemoverCliente {
        @Test
        void deveRemoverCliente() throws Exception {
            var cliente = ClienteHelper.gerarRegistro();
            var dto = ClienteDto.fromEntity(cliente);
            when(clienteService.excluirCliente(dto)).thenReturn(true);

            ResponseEntity<?> response = clienteController.excluirCliente(dto);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("Cliente excluido com sucesso.", response.getBody());
        }

        // Attempt to delete a client that does not exist
        @Test
        public void naoDeveRemoverCliente() throws Exception {
            var cliente = ClienteHelper.gerarRegistro();
            var dto = ClienteDto.fromEntity(cliente);
            Mockito.when(clienteService.excluirCliente(dto)).thenReturn(false);

            ResponseEntity<?> response = clienteController.excluirCliente(dto);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("Falha ao excluir cliente.", response.getBody());
        }
    }

    @Nested
    class BuscarCliente {
        @Test
        public void devePermitirBuscarClientePorCpf() {
            String cpf = "11111111111";
            var cliente = ClienteHelper.gerarRegistro();
            var dto = ClienteDto.fromEntity(cliente);
            when(clienteService.buscarClientePorCpf(cpf)).thenReturn(dto);

            ResponseEntity<ClienteDto> response = clienteController.buscarClientePorCpf(cpf);

            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
            Assertions.assertEquals(dto, response.getBody());
        }

        @Test
        public void devePermitirValidarClienteExistePorCpf_RetornarTrue() {
            String cpf = "12345678901";
            when(clienteService.existeClientePorCpf(cpf)).thenReturn(true);

            ResponseEntity<Boolean> response = clienteController.existeCliente(cpf);

            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
            Assertions.assertTrue(response.getBody());
        }

        @Test
        public void devePermitirValidarClienteExistePorCpf_RetornarFalse() {
            String cpf = "";
            when(clienteService.existeClientePorCpf(cpf)).thenReturn(false);

            ResponseEntity<Boolean> response = clienteController.existeCliente(cpf);

            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
            Assertions.assertFalse(response.getBody());
        }
    }

    @Nested
    class ListarClientes {
        @Test
        public void devePermitirListarClientes() {
            var cliente = ClienteHelper.gerarRegistro();
            var dto = ClienteDto.fromEntity(cliente);
            Pageable pageable = PageRequest.of(0, 10);
            Page<ClienteDto> page = new PageImpl<>(List.of(dto));

            Mockito.when(clienteService.listarClientes(pageable)).thenReturn(page);

            ResponseEntity<Page<ClienteDto>> response = clienteController.listarClientes(0, 10);

            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
            Assertions.assertNotNull(response.getBody());
            Assertions.assertEquals(1, response.getBody().getTotalElements());
        }
    }

}
