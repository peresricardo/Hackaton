package br.com.fiap.srvcliente.dto;

import br.com.fiap.srvcliente.model.ClienteModel;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record ClienteDto(
        @NotBlank(message = "Cpf é preenchimento obrigatório")
        String cpf,
        @Length(min = 5, max = 60, message = "Nome dever ser preenchido entre 5 a 60 caracteres")
        String nome,
        @Length(min = 5, max = 80, message = "Email dever ser preenchido entre 5 a 80 caracteres")
        String email,
        @Length(min = 9, max = 20, message = "Telefone dever ser preenchido entre 9 a 20 caracteres")
        String telefone,
        @NotBlank(message = "Endereço é preenchimento obrigatório")
        @Length(min = 5, max = 80, message = "Logradouro dever ser preenchido entre 5 a 80 caracteres")
        String rua,
        @NotBlank(message = "Cidade é preenchimento obrigatório")
        @Length(min = 5, max = 60, message = "Cidade deve ser preenchido entre 5 a 60 caracteres")
        String cidade,
        @NotBlank(message = "UF é preenchimento obrigatório")
        @Length(min = 2, max = 20, message = "UF deve ser preenchido entre 2 a 20 caracteres")
        String estado,
        @NotBlank(message = "Cep é preenchimento obrigatório")
        @Length(min = 9, max = 9, message = "Cep deve ser preenchido utilizando 9 caracteres")
        String cep,
        @NotBlank(message = "Pais é preenchimento obrigatório")
        @Length(min = 3, max = 40, message = "Pais deve ser preenchido entre 3 a 40 caracteres")
        String pais

) {
        public static ClienteDto fromEntity(ClienteModel cliente) {
                return new ClienteDto(
                        cliente.getCpf(),
                        cliente.getNome(),
                        cliente.getEmail(),
                        cliente.getTelefone(),
                        cliente.getRua(),
                        cliente.getCidade(),
                        cliente.getEstado(),
                        cliente.getCep(),
                        cliente.getPais()
                );
        }
}
