package br.com.fiap.srvCartao.controller;

import br.com.fiap.srvCartao.model.Cartao;
import br.com.fiap.srvCartao.service.CartaoService;
import br.com.fiap.srvCartao.utils.CartaoMock;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartaoControllerTest {

    @Mock
    private CartaoService cartaoService;

    @InjectMocks
    private CartaoController cartaoController;

    @Nested
    class CadastrarCartaoTests {

        @Test
        void cadastrarCartao_ComSucesso() {
            Cartao cartao = CartaoMock.createCartao();
            when(cartaoService.cadastrarCartao(cartao)).thenReturn(cartao);

            ResponseEntity<Cartao> response = cartaoController.cadastrarCartao(cartao);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(cartao, response.getBody());
        }
    }

    @Nested
    class BuscarCartaoPorCpfTests {
        @Test
        void buscarCartaoPorCpf_ComSucesso() {
            String cpf = "12345678900";
            List<Cartao> cartoes = List.of(CartaoMock.createCartao());
            when(cartaoService.buscarCartaoPorCpf(cpf)).thenReturn(cartoes);

            ResponseEntity<List<Cartao>> response = cartaoController.buscarCartaoPorCpf(cpf);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(cartoes, response.getBody());
        }
    }

    @Nested
    class ObterCartaoPorNumeroTests {
        @Test
        void obterCartaoPorNumero_ComSucesso() {
            String numero = "1111222233334444";
            Cartao cartao = CartaoMock.createCartao();
            when(cartaoService.obterCartaoPorNumero(numero)).thenReturn(cartao);

            ResponseEntity<Cartao> response = cartaoController.obterCartaoPorNumero(numero);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(cartao, response.getBody());
        }
    }

    @Nested
    class AtualizarCartaoTests {
        @Test
        void atualizarCartao_ComSucesso() {
            String numero = "1111222233334444";
            Cartao cartao = CartaoMock.createCartao();
            when(cartaoService.atualizarCartao(numero, cartao)).thenReturn(cartao);

            ResponseEntity<Cartao> response = cartaoController.atualizarCartao(numero, cartao);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(cartao, response.getBody());
        }
    }

    @Nested
    class DeletarCartaoTests {
        @Test
        void deletarCartao_ComSucesso() {
            String numero = "1111222233334444";

            ResponseEntity<Void> response = cartaoController.deletarCartao(numero);

            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        }
    }

}