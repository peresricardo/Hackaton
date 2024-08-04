package br.com.fiap.srvPagamento.service.impl;

import br.com.fiap.srvPagamento.client.CartaoEndpointService;
import br.com.fiap.srvPagamento.client.ClienteEndpointService;
import br.com.fiap.srvPagamento.dto.CartaoDto;
import br.com.fiap.srvPagamento.dto.PagamentoPorClienteDto;
import br.com.fiap.srvPagamento.exception.MensagemNotFoundException;
import br.com.fiap.srvPagamento.model.Pagamento;
import br.com.fiap.srvPagamento.repository.PagamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PagamentoServiceImplTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @Mock
    private ClienteEndpointService clienteService;

    @Mock
    private CartaoEndpointService cartaoService;

    @InjectMocks
    private PagamentoServiceImpl pagamentoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastrarPagamentoClienteNaoExiste() {
        Pagamento pagamento = new Pagamento();
        pagamento.setCpf("12345678900");

        when(clienteService.existeCliente(anyString())).thenReturn(ResponseEntity.ok(false));

        Exception exception = assertThrows(MensagemNotFoundException.class, () -> {
            pagamentoService.cadastrarPagamento(pagamento);
        });

        assertEquals("Cliente não encontrado", exception.getMessage());
    }

    @Test
    void testCadastrarPagamentoCartaoNaoExiste() {
        Pagamento pagamento = new Pagamento();
        pagamento.setCpf("12345678900");
        pagamento.setNumero("4111111111111111");

        when(clienteService.existeCliente(anyString())).thenReturn(ResponseEntity.ok(true));
        when(cartaoService.cartaoPorNumero(anyString())).thenReturn(ResponseEntity.of(Optional.empty()));

        Exception exception = assertThrows(MensagemNotFoundException.class, () -> {
            pagamentoService.cadastrarPagamento(pagamento);
        });

        assertEquals("Cartão inexistente", exception.getMessage());
    }

    @Test
    void testCadastrarPagamentoCartaoCpfNaoCorresponde() {
        Pagamento pagamento = new Pagamento();
        pagamento.setCpf("12345678900");
        pagamento.setNumero("4111111111111111");

        CartaoDto cartaoDto = new CartaoDto();
        cartaoDto.setCpf("09876543211");

        when(clienteService.existeCliente(anyString())).thenReturn(ResponseEntity.ok(true));
        when(cartaoService.cartaoPorNumero(anyString())).thenReturn(ResponseEntity.ok(cartaoDto));

        Exception exception = assertThrows(MensagemNotFoundException.class, () -> {
            pagamentoService.cadastrarPagamento(pagamento);
        });

        assertEquals("Cartão não pertence a esse cliente", exception.getMessage());
    }

    @Test
    void testCadastrarPagamentoLimiteInsuficiente() {
        Pagamento pagamento = new Pagamento();
        pagamento.setCpf("12345678900");
        pagamento.setNumero("4111111111111111");
        pagamento.setValor(new BigDecimal("1000"));

        CartaoDto cartaoDto = new CartaoDto();
        cartaoDto.setCpf("12345678900");
        cartaoDto.setLimite(new BigDecimal("500"));

        when(clienteService.existeCliente(anyString())).thenReturn(ResponseEntity.ok(true));
        when(cartaoService.cartaoPorNumero(anyString())).thenReturn(ResponseEntity.ok(cartaoDto));

        Exception exception = assertThrows(MensagemNotFoundException.class, () -> {
            pagamentoService.cadastrarPagamento(pagamento);
        });

        assertEquals("Limite insuficiente para a compra", exception.getMessage());
    }

    @Test
    void testCadastrarPagamentoCvvIncorreto() {
        // Criando um objeto Pagamento e configurando suas propriedades
        Pagamento pagamento = new Pagamento();
        pagamento.setCpf("12345678900");
        pagamento.setNumero("4111111111111111");
        pagamento.setCvv("123");
        pagamento.setValor(new BigDecimal("100"));

        CartaoDto cartaoDto = new CartaoDto();
        cartaoDto.setCpf("12345678900");
        cartaoDto.setCvv("321");
        cartaoDto.setLimite(new BigDecimal("1000"));

        when(clienteService.existeCliente(anyString())).thenReturn(ResponseEntity.ok(true));
        when(cartaoService.cartaoPorNumero(anyString())).thenReturn(ResponseEntity.ok(cartaoDto));

        Exception exception = assertThrows(MensagemNotFoundException.class, () -> {
            pagamentoService.cadastrarPagamento(pagamento);
        });

        assertEquals("Código CVV incorreto, compra recusada", exception.getMessage());
    }


    @Test
    void testCadastrarPagamentoComSucesso() {
        Pagamento pagamento = new Pagamento();
        pagamento.setCpf("12345678900");
        pagamento.setNumero("4111111111111111");
        pagamento.setCvv("123");
        pagamento.setValor(new BigDecimal("100"));

        CartaoDto cartaoDto = new CartaoDto();
        cartaoDto.setCpf("12345678900");
        cartaoDto.setCvv("123");
        cartaoDto.setLimite(new BigDecimal("1000"));

        when(clienteService.existeCliente(anyString())).thenReturn(ResponseEntity.ok(true));
        when(cartaoService.cartaoPorNumero(anyString())).thenReturn(ResponseEntity.ok(cartaoDto));
        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamento);

        Pagamento result = pagamentoService.cadastrarPagamento(pagamento);

        assertNotNull(result);
        assertEquals(pagamento.getCpf(), result.getCpf());
        assertEquals(pagamento.getNumero(), result.getNumero());
        assertEquals(pagamento.getValor(), result.getValor());
    }

    @Test
    void testListaPagamentosPorCliente() {
        Pagamento pagamento1 = new Pagamento();
        pagamento1.setCpf("12345678900");
        pagamento1.setValor(new BigDecimal("100"));

        Pagamento pagamento2 = new Pagamento();
        pagamento2.setCpf("12345678900");
        pagamento2.setValor(new BigDecimal("200"));

        when(pagamentoRepository.findAllByCpf(anyString())).thenReturn(Arrays.asList(pagamento1, pagamento2));

        List<PagamentoPorClienteDto> result = pagamentoService.listaPagamentosPorCliente("12345678900");

        assertNotNull(result);
        assertEquals(2, result.size());

        PagamentoPorClienteDto dto1 = result.get(0);
        assertEquals(new BigDecimal("100"), dto1.getValor());
        assertEquals("cartão de crédito", dto1.getMetodoPagamento());
        assertEquals("compra", dto1.getDescricao());
        assertEquals("aprovado", dto1.getStatus());

        PagamentoPorClienteDto dto2 = result.get(1);
        assertEquals(new BigDecimal("200"), dto2.getValor());
        assertEquals("cartão de crédito", dto2.getMetodoPagamento());
        assertEquals("compra", dto2.getDescricao());
        assertEquals("aprovado", dto2.getStatus());
    }
}
