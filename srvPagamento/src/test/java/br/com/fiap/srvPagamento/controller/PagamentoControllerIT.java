package br.com.fiap.srvPagamento.controller;

import br.com.fiap.srvPagamento.dto.PagamentoPorClienteDto;
import br.com.fiap.srvPagamento.model.Pagamento;
import br.com.fiap.srvPagamento.service.PagamentoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PagamentoController.class)
class PagamentoControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PagamentoService pagamentoService;

    private Pagamento pagamento;
    private PagamentoPorClienteDto pagamentoDto;

    @BeforeEach
    void setUp() {
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
    void testCadastrarPagamento() throws Exception {
        when(pagamentoService.cadastrarPagamento(any(Pagamento.class))).thenReturn(pagamento);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/pagamentos")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(pagamento)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.cpf").value("12345678900"))
                .andExpect(jsonPath("$.numero").value("4111111111111111"))
                .andExpect(jsonPath("$.cvv").value("123"))
                .andExpect(jsonPath("$.valor").value(100))
                .andExpect(jsonPath("$.data_validade").value("01/29"))
                .andDo(print());
    }

    @Test
    void testListaPagamentosPorCliente() throws Exception {
        when(pagamentoService.listaPagamentosPorCliente(anyString())).thenReturn(Collections.singletonList(pagamentoDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/pagamentos/cliente/12345678900")
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].valor").value(100))
                .andExpect(jsonPath("$[0].metodoPagamento").value("cartão de crédito"))
                .andExpect(jsonPath("$[0].descricao").value("compra"))
                .andExpect(jsonPath("$[0].status").value("aprovado"))
                .andDo(print());
    }
}
