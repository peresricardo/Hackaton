package br.com.fiap.srvCartao.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_cartao")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cartao {

    @Id
    private String cpf;
    @Column(name = "limite", nullable = false)
    private BigDecimal limite;
    @Column(name = "numero", nullable = false)
    private String numero;
    @Column(name = "data_validade", nullable = false)
    private String dataValidade;
    @Column(name = "cvv", nullable = false)
    private String cvv;
}
