package br.com.fiap.srvCartao.utils;

import br.com.fiap.srvCartao.model.Cartao;

import java.math.BigDecimal;

public class CartaoMock {

    public static Cartao createCartao(String numero, String cpf) {
        return Cartao.builder()
                .numero(numero)
                .cpf(cpf)
                .limite(BigDecimal.valueOf(5000.0))
                .data_validade("12/25")
                .cvv("123")
                .build();
    }

    public static Cartao createCartao() {
        return createCartao("1111222233334444", "12345678900");
    }
}
