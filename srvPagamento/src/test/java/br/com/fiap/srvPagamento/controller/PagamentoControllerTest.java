package br.com.fiap.srvPagamento.controller;

import br.com.fiap.srvPagamento.dto.PagamentoPorClienteDto;
import br.com.fiap.srvPagamento.exception.LimiteCartaoException;
import br.com.fiap.srvPagamento.exception.MensagemNotFoundException;
import br.com.fiap.srvPagamento.model.Pagamento;
import br.com.fiap.srvPagamento.service.PagamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class PagamentoControllerTest {

    @InjectMocks
    private PagamentoController pagamentoController;

    @Mock
    private PagamentoService pagamentoService;

    private Pagamento pagamento;
    private PagamentoPorClienteDto pagamentoDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pagamento = new Pagamento();
        pagamento.setCpf("12345678900");
        pagamento.setNumero("4111111111111111");
        pagamento.setCvv("123");
        pagamento.setValor(new BigDecimal("100"));
        pagamento.setData_validade("01/29");

        pagamentoDto = new PagamentoPorClienteDto();
        pagamentoDto.setValor(new BigDecimal("100"));
        pagamentoDto.setMetodoPagamento("cartão de crédito");
        pagamentoDto.setDescricao("compra");
        pagamentoDto.setStatus("aprovado");
    }

    @Test
    void testCadastrarPagamento() {
        when(pagamentoService.cadastrarPagamento(any(Pagamento.class))).thenReturn(pagamento);

        ResponseEntity<?> response = pagamentoController.cadastrarPagamento(pagamento);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(pagamento, response.getBody());
    }

    @Test
    void testListaPagamentosPorCliente() {
        when(pagamentoService.listaPagamentosPorCliente(anyString())).thenReturn(Collections.singletonList(pagamentoDto));

        ResponseEntity<?> response = pagamentoController.listaPagamentosPorCliente("12345678900");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(Collections.singletonList(pagamentoDto), response.getBody());
    }

    @Test
    void testCadastrarPagamentoNaoAutorizado() {
        when(pagamentoService.cadastrarPagamento(any(Pagamento.class))).thenThrow(new SecurityException("Unauthorized"));

        ResponseEntity<?> response = pagamentoController.cadastrarPagamento(pagamento);

        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    void testListaPagamentosPorClienteNaoAutorizado() {
        when(pagamentoService.listaPagamentosPorCliente(anyString())).thenThrow(new SecurityException("Unauthorized"));

        ResponseEntity<?> response = pagamentoController.listaPagamentosPorCliente("12345678900");

        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    void testCadastrarPagamentoErroServidor() {
        when(pagamentoService.cadastrarPagamento(any(Pagamento.class))).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<?> response = pagamentoController.cadastrarPagamento(pagamento);

        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testListaPagamentosPorClienteErroServidor() {
        when(pagamentoService.listaPagamentosPorCliente(anyString())).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<?> response = pagamentoController.listaPagamentosPorCliente("123456278900");

        assertEquals(500, response.getStatusCodeValue());
    }
}
