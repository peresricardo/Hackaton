package br.com.fiap.srvCartao.service.impl;

import br.com.fiap.srvCartao.exception.MensagemForBidden;
import br.com.fiap.srvCartao.exception.MensagemFoundException;
import br.com.fiap.srvCartao.exception.MensagemNotFoundException;
import br.com.fiap.srvCartao.model.Cartao;
import br.com.fiap.srvCartao.repository.CartaoRepository;
import br.com.fiap.srvCartao.service.ClienteEndpointService;
import br.com.fiap.srvCartao.utils.CartaoMock;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CartaoServiceImplIT {

    @Autowired
    private CartaoRepository cartaoRepository;

    @MockBean
    private ClienteEndpointService clienteEndpointService;

    private CartaoServiceImpl cartaoService;

    @BeforeEach
    void setUp() {
        cartaoService = new CartaoServiceImpl(cartaoRepository, clienteEndpointService);
    }

    @Nested
    class CadastrarCartaoTests {

        @Test
        void cadastrarCartao_ComSucesso() {
            Cartao cartao = CartaoMock.createCartao();

            when(clienteEndpointService.existeCliente(cartao.getCpf())).thenReturn(ResponseEntity.ok(true));

            Cartao result = cartaoService.cadastrarCartao(cartao);

            assertNotNull(result);
            assertEquals(cartao.getCpf(), result.getCpf());
            assertEquals(cartao.getNumero(), result.getNumero());
        }

        @Test
        void cadastrarCartao_ClienteNaoEncontrado() {
            Cartao cartao = CartaoMock.createCartao();

            when(clienteEndpointService.existeCliente(cartao.getCpf())).thenReturn(ResponseEntity.ok(false));

            MensagemNotFoundException exception = assertThrows(MensagemNotFoundException.class, () -> cartaoService.cadastrarCartao(cartao));
            assertEquals("Cliente não encontrado", exception.getMessage());
        }

        @Test
        void cadastrarCartao_LimiteMaximoAtingido() {
            String cpf = "12345678900";
            Cartao cartao1 = CartaoMock.createCartao("1111222233334444", cpf);
            Cartao cartao2 = CartaoMock.createCartao("1111222233334445", cpf);
            Cartao cartao3 = CartaoMock.createCartao("1111222233334446", cpf);

            when(clienteEndpointService.existeCliente(cpf)).thenReturn(ResponseEntity.ok(true));

            cartaoRepository.save(cartao1);
            cartaoRepository.save(cartao2);

            MensagemForBidden exception = assertThrows(MensagemForBidden.class, () -> cartaoService.cadastrarCartao(cartao3));
            assertEquals("Não é possivel cadastrar mais cartões para esse cpf. Limite máximo atingido!", exception.getMessage());
        }

        @Test
        void cadastrarCartao_CartaoDuplicado() {
            Cartao cartao = CartaoMock.createCartao();

            when(clienteEndpointService.existeCliente(cartao.getCpf())).thenReturn(ResponseEntity.ok(true));
            cartaoRepository.save(cartao);

            MensagemFoundException exception = assertThrows(MensagemFoundException.class, () -> cartaoService.cadastrarCartao(cartao));
            assertEquals("Já existe cartão cadastrado com esse número", exception.getMessage());
        }

        @Test
        void cadastrarCartao_FalhaAoAcessarEndpointCliente() {
            Cartao cartao = CartaoMock.createCartao();

            when(clienteEndpointService.existeCliente(cartao.getCpf())).thenThrow(new RuntimeException("Serviço indisponível"));

            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                cartaoService.cadastrarCartao(cartao);
            });

            assertEquals("Falha ao acessar endopoint cliente.", exception.getMessage());
        }
    }

    @Nested
    class ObterCartaoPorNumeroTests {

        @Test
        void obterCartaoPorNumero_ComSucesso() {
            Cartao cartao = CartaoMock.createCartao();
            cartaoRepository.save(cartao);

            Cartao result = cartaoService.obterCartaoPorNumero(cartao.getNumero());

            assertNotNull(result);
            assertEquals(cartao.getNumero(), result.getNumero());
        }

        @Test
        void obterCartaoPorNumero_NaoEncontrado() {
            assertThrows(MensagemForBidden.class, () -> cartaoService.obterCartaoPorNumero("1111222233334444"));
        }
    }

    @Nested
    class AtualizarCartaoTests {

        @Test
        void atualizarCartao_ComSucesso() {
            Cartao cartaoExistente = CartaoMock.createCartao();
            cartaoRepository.save(cartaoExistente);

            Cartao cartaoAtualizado = CartaoMock.createCartao();
            cartaoAtualizado.setLimite(BigDecimal.valueOf(6000.0));
            cartaoAtualizado.setData_validade("01/26");
            cartaoAtualizado.setCvv("456");

            Cartao result = cartaoService.atualizarCartao(cartaoExistente.getNumero(), cartaoAtualizado);

            assertNotNull(result);
            assertEquals(cartaoAtualizado.getLimite(), result.getLimite());
            assertEquals(cartaoAtualizado.getData_validade(), result.getData_validade());
            assertEquals(cartaoAtualizado.getCvv(), result.getCvv());
        }

        @Test
        void atualizarCartao_NaoEncontrado() {
            Cartao cartaoAtualizado = CartaoMock.createCartao();
            cartaoAtualizado.setLimite(BigDecimal.valueOf(6000.0));

            assertThrows(MensagemNotFoundException.class, () -> cartaoService.atualizarCartao("1111222233334444", cartaoAtualizado));
        }
    }

    @Nested
    class DeletarCartaoTests {

        @Test
        void deletarCartao_ComSucesso() {
            Cartao cartao = CartaoMock.createCartao("1111222233334444", "12345678900");
            cartaoRepository.save(cartao);

            cartaoService.deletarCartao(cartao.getNumero());

            assertNull(cartaoRepository.findByNumero(cartao.getNumero()));
        }

        @Test
        void deletarCartao_NaoEncontrado() {
            assertThrows(MensagemNotFoundException.class, () -> cartaoService.deletarCartao("1111222233334444"));
        }
    }

    @Nested
    class BuscarCartaoPorCpfTests {

        @Test
        void buscarCartaoPorCpf_ComSucesso() {
            String cpf = "12345678900";
            Cartao cartao1 = CartaoMock.createCartao("1111222233334444", cpf);
            Cartao cartao2 = CartaoMock.createCartao("1111222233334445", cpf);
            cartaoRepository.save(cartao1);
            cartaoRepository.save(cartao2);

            when(clienteEndpointService.existeCliente(cpf)).thenReturn(ResponseEntity.ok(true));

            List<Cartao> result = cartaoService.buscarCartaoPorCpf(cpf);

            assertNotNull(result);
            assertEquals(2, result.size());
        }

        @Test
        void buscarCartaoPorCpf_ClienteNaoEncontrado() {
            String cpf = "12345678900";

            when(clienteEndpointService.existeCliente(cpf)).thenReturn(ResponseEntity.ok(false));

            MensagemNotFoundException exception = assertThrows(MensagemNotFoundException.class, () -> cartaoService.buscarCartaoPorCpf(cpf));
            assertEquals("Cliente não encontrado", exception.getMessage());
        }
    }
}