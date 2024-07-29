package br.com.fiap.srvcliente.model;

import br.com.fiap.srvcliente.dto.ClienteDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_cliente")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ClienteModel {
    @Id
    @Column(nullable = false, length = 11)
    private String cpf;
    @Column(nullable = false, length = 60)
    private String nome;
    @Column(length = 80)
    private String email;
    @Column(length = 20)
    private String telefone;
    @Column(nullable = false, length = 80)
    private String rua;
    @Column(nullable = false, length = 60)
    private String cidade;
    @Column(nullable = false, length = 20)
    private String estado;
    @Column(nullable = false, length = 9)
    private String cep;
    @Column(nullable = false, length = 40)
    private String pais;


    public ClienteModel(ClienteDto clienteDto) {
        this.cpf = clienteDto.cpf();
        this.nome = clienteDto.nome();
        this.email = clienteDto.email();
        this.telefone = clienteDto.telefone();
        this.rua = clienteDto.rua();
        this.cidade = clienteDto.cidade();
        this.estado = clienteDto.estado();
        this.cep = clienteDto.cep();
        this.pais = clienteDto.pais();
    }
}
