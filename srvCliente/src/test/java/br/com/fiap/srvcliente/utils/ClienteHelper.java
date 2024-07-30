package br.com.fiap.srvcliente.utils;

import br.com.fiap.srvcliente.model.ClienteModel;
import br.com.fiap.srvcliente.repository.ClienteRepository;

public class ClienteHelper {
    public static ClienteModel gerarRegistro() {
        var entity = ClienteModel.builder()
                .cpf("11111111111")
                .nome("Nome do Cliente")
                .email("email@email.com")
                .telefone("123456")
                .rua("Rua do Cliente")
                .cidade("Cidade do Cliente")
                .estado("Estado")
                .cep("00000000")
                .pais("Brasil")
                .build();
        return entity;
    }

    public static ClienteModel RegistrarCliente(ClienteModel model, ClienteRepository repository) {
        return repository.save(model);
    }
}
