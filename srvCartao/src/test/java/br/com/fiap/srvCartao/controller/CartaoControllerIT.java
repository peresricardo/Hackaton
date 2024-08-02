package br.com.fiap.srvCartao.controller;

import br.com.fiap.srvCartao.model.Cartao;
import br.com.fiap.srvCartao.repository.CartaoRepository;
import br.com.fiap.srvCartao.service.CartaoService;
import br.com.fiap.srvCartao.utils.CartaoMock;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CartaoControllerIT {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CartaoRepository cartaoRepository;

    @MockBean
    private CartaoService cartaoService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Nested
    class CadastrarCartaoTests {

        @Test
        void cadastrarCartao_ComSucesso() throws Exception {
            String cartaoJson = """
                {
                  "numero": "1391229109239284",
                  "cpf": "81625977856",
                  "limite": 1,
                  "data_validade": "10/74",
                  "cvv": "335"
                }
            """;
            Cartao cartao = CartaoMock.createCartao("1391229109239284", "81625977856");
            when(cartaoService.cadastrarCartao(cartao)).thenReturn(cartao);

            mockMvc.perform(post("/api/cartao")
                            .contentType("application/json")
                            .content(cartaoJson))
                    .andExpect(status().isCreated());
        }
    }

    @Nested
    class BuscarCartaoPorCpfTests {
        @Test
        void buscarCartaoPorCpf_ComSucesso() throws Exception {
            String cpf = "12345678900";
            Cartao cartao = CartaoMock.createCartao();
            List<Cartao> cartoes = List.of(cartao);
            when(cartaoService.buscarCartaoPorCpf(cpf)).thenReturn(cartoes);

            mockMvc.perform(get("/api/cartao/cpf/{cpf}", cpf))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].cpf", is(cartao.getCpf())));
        }
    }

    @Nested
    class ObterCartaoPorNumeroTests {
        @Test
        void obterCartaoPorNumero_ComSucesso() throws Exception {
            String numero = "1111222233334444";
            Cartao cartao = CartaoMock.createCartao();
            when(cartaoService.obterCartaoPorNumero(numero)).thenReturn(cartao);

            mockMvc.perform(get("/api/cartao/numero/{numero}", numero))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.numero", is(cartao.getNumero())));
        }
    }

    @Nested
    class AtualizarCartaoTests {
        @Test
        void atualizarCartao_ComSucesso() throws Exception {
            String numero = "1111222233334444";
            String cartaoJson = """
                {
                  "numero": "7590664404596508",
                  "cpf": "83958765267",
                  "limite": 1,
                  "data_validade": "03/70",
                  "cvv": "325"
                }
            """;
            Cartao cartao = CartaoMock.createCartao("7590664404596508", "83958765267");
            when(cartaoService.atualizarCartao(numero, cartao)).thenReturn(cartao);

            mockMvc.perform(put("/api/cartao/{numero}", numero)
                            .contentType("application/json")
                            .content(cartaoJson))
                    .andExpect(status().isOk());
        }
    }

    @Nested
    class DeletarCartaoTests {
        @Test
        void deletarCartao_ComSucesso() throws Exception {
            String numero = "1111222233334444";

            mockMvc.perform(delete("/api/cartao/{numero}", numero))
                    .andExpect(status().isNoContent());
        }
    }
}