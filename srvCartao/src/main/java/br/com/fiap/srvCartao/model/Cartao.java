package br.com.fiap.srvCartao.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tb_cartao")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "cpf", nullable = false)
    private String cpf;
    @Column(name = "limite", nullable = false)
    private BigDecimal limite;
    @Column(name = "numero", nullable = false)
    private String numero;
    @Column(name = "data_validade", nullable = false)
    private String data_validade;
    @Column(name = "cvv", nullable = false)
    private String cvv;
}
