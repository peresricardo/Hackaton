package br.com.fiap.srvPagamento.service.impl;

import br.com.fiap.srvPagamento.dto.CartaoDto;
import br.com.fiap.srvPagamento.dto.PagamentoPorClienteDto;
import br.com.fiap.srvPagamento.exception.MensagemNotFoundException;
import br.com.fiap.srvPagamento.model.Pagamento;
import br.com.fiap.srvPagamento.repository.PagamentoRepository;

import br.com.fiap.srvPagamento.client.ClienteEndpointService;
import br.com.fiap.srvPagamento.client.CartaoEndpointService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class PagamentoServiceImplIT {

    @Autowired
    private PagamentoServiceImpl pagamentoService;

    @MockBean
    private PagamentoRepository pagamentoRepository;

    @MockBean
    private ClienteEndpointService clienteService;

    @MockBean
    private CartaoEndpointService cartaoService;

    private Pagamento pagamento;
    private CartaoDto cartaoDto;

    @BeforeEach
    void setUp() {
        pagamento = new Pagamento();
        pagamento.setCpf("12345678900");
        pagamento.setNumero("4111111111111111");
        pagamento.setCvv("123");
        pagamento.setValor(new BigDecimal("100"));

        cartaoDto = new CartaoDto();
        cartaoDto.setCpf("12345678900");
        cartaoDto.setCvv("123");
        cartaoDto.setLimite(new BigDecimal("500"));
    }

    @Test
    void testCadastrarPagamentoCvvIncorreto() {
        cartaoDto.setCvv("321"); // CVV diferente para simular erro

        when(clienteService.existeCliente(anyString())).thenReturn(ResponseEntity.ok(true));
        when(cartaoService.cartaoPorNumero(anyString())).thenReturn(ResponseEntity.ok(cartaoDto));

        Exception exception = assertThrows(MensagemNotFoundException.class, () -> {
            pagamentoService.cadastrarPagamento(pagamento);
        });

        assertEquals("Código CVV incorreto, compra recusada", exception.getMessage());
    }

    @Test
    void testCadastrarPagamentoSucesso() {
        when(clienteService.existeCliente(anyString())).thenReturn(ResponseEntity.ok(true));
        when(cartaoService.cartaoPorNumero(anyString())).thenReturn(ResponseEntity.ok(cartaoDto));
        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamento);

        Pagamento resultado = pagamentoService.cadastrarPagamento(pagamento);

        assertNotNull(resultado);
        assertEquals(pagamento.getCpf(), resultado.getCpf());
        assertEquals(pagamento.getNumero(), resultado.getNumero());
    }

    @Test
    void testListaPagamentosPorCliente() {
        String cpf = "12345678900";

        Pagamento pagamento1 = new Pagamento();
        pagamento1.setCpf(cpf);
        pagamento1.setValor(new BigDecimal("100"));

        Pagamento pagamento2 = new Pagamento();
        pagamento2.setCpf(cpf);
        pagamento2.setValor(new BigDecimal("200"));

        when(pagamentoRepository.findAllByCpf(anyString())).thenReturn(Arrays.asList(pagamento1, pagamento2));

        List<PagamentoPorClienteDto> pagamentos = pagamentoService.listaPagamentosPorCliente(cpf);

        assertNotNull(pagamentos);
        assertEquals(2, pagamentos.size());
        assertEquals(new BigDecimal("100"), pagamentos.get(0).getValor());
        assertEquals(new BigDecimal("200"), pagamentos.get(1).getValor());
    }
}
