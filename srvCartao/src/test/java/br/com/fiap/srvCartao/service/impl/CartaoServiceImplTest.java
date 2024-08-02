package br.com.fiap.srvCartao.service.impl;

import br.com.fiap.srvCartao.exception.MensagemForBidden;
import br.com.fiap.srvCartao.exception.MensagemFoundException;
import br.com.fiap.srvCartao.exception.MensagemNotFoundException;
import br.com.fiap.srvCartao.model.Cartao;
import br.com.fiap.srvCartao.repository.CartaoRepository;
import br.com.fiap.srvCartao.service.ClienteEndpointService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CartaoServiceImplTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @Mock
    private ClienteEndpointService clienteEndpointService;

    @InjectMocks
    private CartaoServiceImpl cartaoService;

    private EasyRandom easyRandom;

    @BeforeEach
    void setUp() {
        easyRandom = new EasyRandom();
    }

    @Nested
    class CadastrarCartaoTests {

        @Test
        void cadastrarCartao_ComSucesso() {
            Cartao cartao = easyRandom.nextObject(Cartao.class);
            cartao.setCpf("12345678900");
            cartao.setNumero("1111222233334444");

            when(clienteEndpointService.existeCliente(cartao.getCpf())).thenReturn(ResponseEntity.ok(true));
            when(cartaoRepository.findByNumero(cartao.getNumero())).thenReturn(null);
            when(cartaoRepository.countCartaoByCpf(cartao.getCpf())).thenReturn(1);
            when(cartaoRepository.save(cartao)).thenReturn(cartao);

            Cartao result = cartaoService.cadastrarCartao(cartao);

            assertNotNull(result);
            verify(cartaoRepository).save(cartao);
        }

        @Test
        void cadastrarCartao_ClienteNaoEncontrado() {
            Cartao cartao = easyRandom.nextObject(Cartao.class);
            cartao.setCpf("12345678900");

            when(clienteEndpointService.existeCliente(cartao.getCpf())).thenReturn(ResponseEntity.ok(false));

            MensagemNotFoundException exception = assertThrows(MensagemNotFoundException.class, () -> cartaoService.cadastrarCartao(cartao));
            assertEquals("Cliente não encontrado", exception.getMessage());
        }

        @Test
        void cadastrarCartao_LimiteMaximoAtingido() {
            Cartao cartao = easyRandom.nextObject(Cartao.class);
            cartao.setCpf("12345678900");
            cartao.setNumero("1111222233334444");

            when(clienteEndpointService.existeCliente(cartao.getCpf())).thenReturn(ResponseEntity.ok(true));
            when(cartaoRepository.findByNumero(cartao.getNumero())).thenReturn(null);
            when(cartaoRepository.countCartaoByCpf(cartao.getCpf())).thenReturn(2);

            MensagemForBidden exception = assertThrows(MensagemForBidden.class, () -> cartaoService.cadastrarCartao(cartao));
            assertEquals("Não é possivel cadastrar mais cartões para esse cpf. Limite máximo atingido!", exception.getMessage());
        }

        @Test
        void cadastrarCartao_CartaoDuplicado() {
            Cartao cartao = easyRandom.nextObject(Cartao.class);
            cartao.setCpf("12345678900");
            cartao.setNumero("1111222233334444");

            when(clienteEndpointService.existeCliente(cartao.getCpf())).thenReturn(ResponseEntity.ok(true));
            when(cartaoRepository.findByNumero(cartao.getNumero())).thenReturn(cartao);

            MensagemFoundException exception = assertThrows(MensagemFoundException.class, () -> cartaoService.cadastrarCartao(cartao));
            assertEquals("Já existe cartão cadastrado com esse número", exception.getMessage());
        }
    }

    @Nested
    class ObterCartaoPorNumeroTests {

        @Test
        void obterCartaoPorNumero_ComSucesso() {
            Cartao cartao = easyRandom.nextObject(Cartao.class);
            cartao.setNumero("1111222233334444");

            when(cartaoRepository.findByNumero(cartao.getNumero())).thenReturn(cartao);

            Cartao result = cartaoService.obterCartaoPorNumero(cartao.getNumero());

            assertNotNull(result);
            assertEquals(cartao.getNumero(), result.getNumero());
        }

        @Test
        void obterCartaoPorNumero_NaoEncontrado() {
            when(cartaoRepository.findByNumero("1111222233334444")).thenReturn(null);

            assertThrows(MensagemForBidden.class, () -> cartaoService.obterCartaoPorNumero("1111222233334444"));
        }
    }

    @Nested
    class AtualizarCartaoTests {

        @Test
        void atualizarCartao_ComSucesso() {
            Cartao cartaoExistente = easyRandom.nextObject(Cartao.class);
            cartaoExistente.setNumero("1111222233334444");

            Cartao cartaoAtualizado = easyRandom.nextObject(Cartao.class);
            cartaoAtualizado.setLimite(BigDecimal.valueOf(5000.0));
            cartaoAtualizado.setData_validade("12/25");
            cartaoAtualizado.setCvv("123");

            when(cartaoRepository.findByNumero(cartaoExistente.getNumero())).thenReturn(cartaoExistente);
            when(cartaoRepository.save(cartaoExistente)).thenReturn(cartaoExistente);

            Cartao result = cartaoService.atualizarCartao(cartaoExistente.getNumero(), cartaoAtualizado);

            assertNotNull(result);
            assertEquals(cartaoAtualizado.getLimite(), result.getLimite());
            assertEquals(cartaoAtualizado.getData_validade(), result.getData_validade());
            assertEquals(cartaoAtualizado.getCvv(), result.getCvv());
        }

        @Test
        void atualizarCartao_NaoEncontrado() {
            when(cartaoRepository.findByNumero("1111222233334444")).thenReturn(null);

            Cartao cartaoAtualizado = easyRandom.nextObject(Cartao.class);
            cartaoAtualizado.setLimite(BigDecimal.valueOf(5000.0));

            assertThrows(MensagemNotFoundException.class, () -> cartaoService.atualizarCartao("1111222233334444", cartaoAtualizado));
        }
    }

    @Nested
    class DeletarCartaoTests {

        @Test
        void deletarCartao_ComSucesso() {
            Cartao cartao = easyRandom.nextObject(Cartao.class);
            cartao.setNumero("1111222233334444");

            when(cartaoRepository.findByNumero(cartao.getNumero())).thenReturn(cartao);

            cartaoService.deletarCartao(cartao.getNumero());

            verify(cartaoRepository).delete(cartao);
        }

        @Test
        void deletarCartao_NaoEncontrado() {
            when(cartaoRepository.findByNumero("1111222233334444")).thenReturn(null);

            assertThrows(MensagemNotFoundException.class, () -> cartaoService.deletarCartao("1111222233334444"));
        }
    }

    @Nested
    class BuscarCartaoPorCpfTests {

        @Test
        void buscarCartaoPorCpf_ComSucesso() {
            String cpf = "12345678900";
            List<Cartao> cartoes = List.of(easyRandom.nextObject(Cartao.class), easyRandom.nextObject(Cartao.class));

            when(clienteEndpointService.existeCliente(cpf)).thenReturn(ResponseEntity.ok(true));
            when(cartaoRepository.findByCpf(cpf)).thenReturn(cartoes);

            List<Cartao> result = cartaoService.buscarCartaoPorCpf(cpf);

            assertNotNull(result);
            assertEquals(2, result.size());
            verify(cartaoRepository).findByCpf(cpf);
        }

        @Test
        void buscarCartaoPorCpf_ClienteNaoEncontrado() {
            String cpf = "12345678900";

            when(clienteEndpointService.existeCliente(cpf)).thenReturn(ResponseEntity.ok(false));

            MensagemNotFoundException exception = assertThrows(MensagemNotFoundException.class, () -> cartaoService.buscarCartaoPorCpf(cpf));
            assertEquals("Cliente não encontrado", exception.getMessage());
        }

    }

    @Nested
    class IntegracaoComEndpointClienteTests {

        @Test
        void verificaClienteExiste_FalhaNaComunicacao() {
            String cpf = "12345678900";
            Cartao cartao = easyRandom.nextObject(Cartao.class);
            cartao.setCpf(cpf);

            when(clienteEndpointService.existeCliente(cpf)).thenThrow(new RuntimeException("Falha ao acessar endpoint cliente"));

            assertThrows(RuntimeException.class, () -> cartaoService.cadastrarCartao(cartao));
        }
    }
}