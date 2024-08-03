package br.com.fiap.srvPagamento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoDto {

    private String numero;
    private String cpf;
    private BigDecimal limite;
    private String dataValidade;
    private String cvv;

}
